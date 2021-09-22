package ar.edu.itba.paw.webapp.forms;


import ar.edu.itba.paw.webapp.forms.Annotations.ValidFile;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class EditArticleForm {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @Min(1)
    private float pricePerDay;

    private List<Long> categories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Long> getCategories(){
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

}