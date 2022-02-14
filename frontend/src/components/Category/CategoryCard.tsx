import {Card} from 'react-bootstrap';
import {LinkContainer} from 'react-router-bootstrap';
import {useListImages} from "../../api/images/imagesSlice";

interface CategoryProps {
    description: string;
    url: string;
    imageUrl: string;
}

export default function CategoryCard(props: CategoryProps) {


    return (
        <LinkContainer to={props.url}>
            <Card className="card-style w-50">
                <Card.Img variant='top' src={props.imageUrl} alt={props.description} height='100rem'/>
                <Card.Body className='d-flex text-center justify-content-center align-items-center'>
                    <Card.Title as="h4">{props.description}</Card.Title>
                </Card.Body>
            </Card>
        </LinkContainer>
    );
}
