package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.webapp.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("articles")
public class ArticleController {

    @Autowired
    private ArticleService as;
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response list(@QueryParam("name") @DefaultValue("") String name,
                         @QueryParam("category") Long category,  @QueryParam("orderBy") Long orderBy,
                         @QueryParam("user") Long user, @QueryParam("location") Long location,
                         @QueryParam("initPrice") Float initPrice,
                         @QueryParam("endPrice") Float endPrice, @QueryParam("page") @DefaultValue("1") long page) {

        final List<ArticleDTO> articles = as.get(name, category, orderBy, user, location, initPrice,
                endPrice, page).stream().map(article -> ArticleDTO.fromArticle(article, uriInfo)).
                collect(Collectors.toList());

        if (articles.isEmpty())
            return Response.noContent().build();

        final long maxPage = as.getMaxPage(name, category, user, location, initPrice, endPrice);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().queryParam("name", name);

        if (category != null)
            uriBuilder.queryParam("category", category);

        if (orderBy != null)
            uriBuilder.queryParam("orderBy", orderBy);

        if (user != null)
            uriBuilder.queryParam("user", user);

        if (location != null)
            uriBuilder.queryParam("location", location);

        if (initPrice != null)
            uriBuilder.queryParam("initPrice", initPrice);

        if (endPrice != null)
            uriBuilder.queryParam("endPrice", endPrice);

        return PaginationProvider.generateResponseWithLinks
                (Response.ok(new GenericEntity<List<ArticleDTO>>(articles) {}), page, maxPage, uriBuilder);
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    public Response createArticle(final ArticleDTO articleDTO) {
        // TODO: not working yet
        final Article article = as.createArticle(articleDTO.getTitle(), articleDTO.getDescription(), articleDTO.getPricePerDay(),
                articleDTO.getCategories(), articleDTO.getImages(), articleDTO.getOwnerId()); // TODO: obtener data de las urls
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(article.getId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getById(@PathParam("id") final long id){
        final Article article = as.findById(id).orElseThrow(ArticleNotFoundException::new);
        return Response.ok(ArticleDTO.fromArticle(article, uriInfo)).build();
    }

    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON,})
    @Path("/{id}")
    public Response modify(@PathParam("id") long id, ArticleDTO articleDTO) {
        as.editArticle(id, articleDTO.getTitle(), articleDTO.getDescription(), articleDTO.getPricePerDay(), articleDTO.getCategories());
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/related")
    public Response related(@PathParam("id") long id) {
        List<ArticleDTO> articles = as.recommendedArticles(id)
                .stream().map((Article article) -> ArticleDTO.fromArticle(article, uriInfo))
                .collect(Collectors.toList());

        if (articles.isEmpty())
            return Response.noContent().build();
        return Response.ok(new GenericEntity<List<ArticleDTO>>(articles) {}).build();
    }
}
