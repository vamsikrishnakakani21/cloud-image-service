***Cloud-native image management service built with Spring Boot and AWS. Features Dockerized deployment on ECS Fargate, image storage in Amazon S3, metadata persistence in Amazon RDS PostgreSQL, event-driven thumbnail generation using AWS Lambda, IAM-based authentication, and CloudWatch monitoring.***                    


                    
                    Client
                       │
                 REST API (Spring Boot)
                       │
                 Amazon ECS Fargate
                       │
             ┌─────────┴──────────┐
             │                    │
      Amazon S3             Amazon RDS
             │
     ObjectCreated Event
             │
        AWS Lambda
             │
    Thumbnail Generation
             │
    Thumbnails / folder

  
