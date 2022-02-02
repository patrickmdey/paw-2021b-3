import {StringCollection} from '../../types'

let enCollection: StringCollection = {
    header: {
        requests: "My requests",
        logout: "Log out",
        profile: "My profile",
        login: "Log in",
        signup: "Register",
        marketplace: "Explore",
        publishArticle: "Publish article",
    },
    footer: {
        team: "Team members",
        contact: "Contact",
        spanish: "Spanish",
        english: "English",
        email: "Email",
        language: "Language"
    },
    filter: {
        title: "Filters",
        name: "Name",
        category: "Category",
        orderBy: "Order by",
        location: "Location",
        minPrice: "Min. price",
        maxPrice: "Max. price",
        button: "Search",
        everyLocation: "Every location",
        everyCategory: "Every category"
    },
    login: {
        title: "LogIn",
        email: "Email*",
        emailPlaceholder: "Enter your email",
        password: "Password*",
        passwordPlaceholder: "Enter your password",
        rememberMe: "Remember me",
        loginButton: "LogIn",
        signupButton: "Register",
        errors: {
            email: "You must enter your email",
            password: "You must enter your passwored"
        },
        error: "Email or password is incorrect"
    },
    article: {
        ownerCardTitle: "Owner",
        descriptionTitle: "Description",
        rent: "rent",
        noArticles: "There are no articles yet",
        timesRented: "rents",
        createArticle: {
            title: "Publish Article",
            create: "Publish",
            pricePerDay: "PricePerDay*",
            articleName: "Name*",
            articleNameLabel: "Enter the article name",
            articleDescription: "Description*",
            articleDescriptionLabel: "Enter the article description",
            selectCategory: "Select at least one category",
            selectImage: "Upload at least one image",
            errors: {
                title: "You must enter a name for the article",
                titleLength: "The name of the article must have between {2} and {1} characters",
                description: "The description must have between {2} and {1} characters",
                categories: "You must select at least one category",
                pricePerDay: "The price must be greater than 1",
                images: "You must upload at least one image"
            }
        },
        requestArticle: {
            title: "Request Article",
            startDate: "Start Date*",
            endDate: "End Date*",
            message: "Message*",
            messagePlaceHolder: "Enter a message so that the owner coinsider your request",
            send: "Send",
            errors: {
                startDate: "You must enter the start date",
                endDate: "You must enter the end date",
                message: "You must enter a message for the owner",
            }
        }

    },
    review: {
        noReviews: "There are no reviews yet",
        reviews: "Reviews"
    },
    categories: {
        searchByCategories: "Search by categories",
    },
    landing: {
        title: "Welcome to RentApp",
        explanation: "In this app you will be able to search and rent any kind of articles just at a click of a button away. Select the one you like the most, the date you need it and Rentapp will put you in contact with the owner",
        button: "View Articles",
        titleLoggedIn: "Welcome {0}",
        textNoRequests: "You have no pending requests",
        pendingRequests: "You have {0} pending rent request",
        acceptedRequests: "You have {0} accepted rent requests",
        rejectedRequests: "You have {0} rejected rent requests",
        viewRequestsButton: "View requests"
    },
    register: {
        title: "Register",
        firstName: "Name*",
        firstNamePlaceholder: "Enter your name",
        lastName: "Last name*",
        lastNamePlaceholder: "Enter your last name",
        email: "Email*",
        emailPlaceholder: "Enter your email",
        location: "Location*",
        password: "Password*",
        passwordPlaceholder: "Enter your password",
        confirmPassword: "Confirm password*",
        confirmPasswordPlaceholder: "Reenter your password",
        image: "Upload image",
        isRenter: "Would you like to rent your stuff? ",
        confirmButton: "Confirm",
        errors: {
            firstName: "You must enter your first name",
            lastName: "You must enter your last name",
            location: "You must select your location",
            image: "You must upload an image"
        }
    },
    requestCard: {
        rejectButton: "Reject",
        acceptButton: "Accept",
        messageTitle: "Message:",
        endDate: "End date: {0}",
        startDate: "Start date: {0}",
        name: "Name: {0} {1}",
        rejectTitle: "Reject request",
        rejectText: "Are you sure you want to reject this request?"
    }

}


export {enCollection}