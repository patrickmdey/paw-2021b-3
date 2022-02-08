import { useListArticles } from '../features/api/articles/articlesSlice';
import { ListArticleParameters } from '../features/api/articles/types';
import ArticleCardList from '../components/ArticleCardList';
import FilterCard from '../components/FilterCard';
import { Col, Container, Row } from 'react-bootstrap';
import usePaginatedResponse from '../hooks/usePaginatedResponse';
import PagesList from '../components/PagesList';
import { useState } from 'react';
import { Helmet } from 'react-helmet';
import { strings } from '../i18n/i18n';
import NoDataCard from '../components/NoData/NoDataCard';

function Marketplace() {
	const [page, setPage] = useState(1);
	const [filters, setFilters] = useState<ListArticleParameters>({});
	const { data, pages, error, isLoading } = usePaginatedResponse(
		useListArticles({
			page: page,
			...filters
		})
	);

	return (
		<>
			<Helmet>
				<title>{strings.collection.pageTitles.marketplace}</title>
			</Helmet>
			<Container>
				<Row className='align-items-start justify-content-center'>
					<Col md={3} lg={3}>
						<FilterCard onSubmit={(data) => setFilters(data)} />
					</Col>
					<Col md={9} lg={9}>
						{error ? (
							<>Oh no, there was an error</>
						) : isLoading ? (
							<>Loading</>
						) : data === null || (data && data.length === 0) ? (
							<NoDataCard
								title={strings.collection.noData.articleNotFoundTitle}
								subtitle={strings.collection.noData.articleNotFoundSubtitle}
							/>
						) : (
							data && (
								<>
									<ArticleCardList articles={data} articlesPerRow={3}></ArticleCardList>
									<PagesList pages={pages} page={page} setPage={setPage} />
								</>
							)
						)}
					</Col>
				</Row>
			</Container>
		</>
	);
}

export default Marketplace;