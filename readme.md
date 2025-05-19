# E-Commerce Application

## Overview
This is a Java Spring Boot e-commerce application that provides a platform for merchants to sell products and customers to purchase them. The application includes features like user authentication, product management, shopping cart functionality, and payment processing through RazorPay integration.

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── jsp/
│   │           └── ecommerce/
│   │               ├── entity/
│   │               │   ├── Customer.java
│   │               │   ├── Merchant.java
│   │               │   ├── Payment.java
│   │               │   └── Product.java
│   │               ├── helper/
│   │               │   └── RazorPayHelper.java
│   │               ├── controller/
│   │               ├── repository/
│   │               ├── service/
│   │               └── dto/
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/
└── test/
```

## Features
- **User Management**: Registration and authentication for customers and merchants
- **Product Management**: CRUD operations for products
- **Order Processing**: Shopping cart and order management
- **Payment Integration**: Secure payment processing with RazorPay

## Technology Stack
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- RazorPay API
- MySQL/PostgreSQL (database)

## Entity Details
- **Customer**: Stores user information with creation timestamp
- **Merchant**: Stores seller information with creation timestamp
- **Product**: Contains product details with update timestamp
- **Payment**: Tracks payment information with creation timestamp

## Payment Integration
The application integrates with RazorPay for payment processing. The `RazorPayHelper` class handles the creation of payment orders:

```java
public String createPayment(double amount) {
    // Creates a payment order with RazorPay
    // Returns the order ID for client-side processing
}
```

## Setup and Installation
1. Clone the repository
```bash
git clone https://github.com/yourusername/ecommerce-app.git
```

2. Configure database settings in `application.properties`

3. Set up RazorPay API keys in `application.properties`

4. Build the project
```bash
mvn clean install
```

5. Run the application
```bash
mvn spring-boot:run
```

## API Documentation
The application exposes RESTful APIs for various operations:
- User registration and authentication
- Product listing and management
- Cart operations
- Order processing
- Payment handling

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.