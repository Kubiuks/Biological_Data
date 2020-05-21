#Client Documentation

## Deployment instructions

This instructions are for setting up the system components from Google. It is possible to use 
alternatives, for example Amazon, and the procedures should be similar. Each website has detailed
instructions that can be found only if necessary. 

### Server

The backend, or the Spring Boot server is the main component of the app. After containerising it, for example with Docker, it can run from
any server. If the client want to, they can use a Virtual Machine from Google Cloud to host it. The best region for it
is *europe-west2-a*, as it is located in London. If the client has his one server, that will also work fine. 

### Storage

It will be necessary to create a Bucket using the Google Cloud Storage. We recommend using *Subject to object ACLs*
as the Public access policy. The program will make the uploaded images visible to only people that have the links. Only
admins will be able to make changes to the bucket content and it will also enable uploading private objects to the 
bucket if the client desires so. The Access Control will be Fine-Grained. Location type should be Multi-region, while 
Location could be *eu (multiple regions in European union)* or something similar, focused on, or including UK.

### Database 

We recommend using MariaDB as the used database. This could run on a Virtual Machine on Google Cloud 
Compute Engine. The best region is *europe-west2-a* since it is located in London. There are ways of 
automatically creating a database instance for example this one :
[MariaDB Certified by Bitnami](https://console.cloud.google.com/marketplace/details/bitnami-launchpad/mariadb). 
The database will require further configuration, including creating an actual database. There as a guide on how to do this 
here: [Create a Database](https://dev.mysql.com/doc/refman/8.0/en/creating-database.html). We recommend creating a new user
and password for connecting to the database, so as to not use the root ones: 
[Create new User](https://dev.mysql.com/doc/refman/8.0/en/create-user.html).

Depending where the Spring Boot server will be located, there may be a need to enable and use SSL for the database. For 
example if both are located on virtual machines using Google Compute, then you could just let the database be accesses 
only from the virtual machine of the server. If the server is located in another place, then SSL for communication will
be necessary: [Enable SSL for Database](https://dev.mysql.com/doc/refman/5.7/en/using-encrypted-connections.html). In
this case, the created user will also have to be usable only over encrypted connections.

### Application Properties

In the folder *src/main/resources*  there is a file named *application.properties* that has all the setting and credentials 
of the app. The field *spring.datasource.url* is used for the database url, for example :
*spring.datasource.url = jdbc:mariadb://database_ip:port/database_name*. The next 3 lines are commented out and are only 
needed if an SSL connection is required with the database. If that is the case, then the second and third line would 
need to be changed with the client and trust certificate path and password. Then *spring.datasource.username* and 
*spring.datasource.password* are the credentials used for accessing the database.

The fields from Keystore for SSL Connections are used for the certificate used for establishing TSL. Those fields are
essentials, since the apps should use HTTPS. 

The fields from Google Properties are the ones that are used for the bucket. The field 
*google.credentials.classpath* specifies where the Google Credentials file is located. If you do not know how to get this,
here is a guide: [Google Credentials](https://cloud.google.com/docs/authentication/getting-started).
The field *google.bucket.galleryPath* specifies the subfolder inside the bucket where the images will be stored.

For the Google Vision Properties field, use the Google Credentials for the account that will use the Vision API. It can be
the same as the one for the bucket. 

The Implementations Configuration - Storage fields are for specifying what storage option to use. By default it will use 
the Google bucket, but it can be configured to use an Amazon bucket if it is desired. In that case, it would be necessary
to complete the fields from AWS Properties. The first field to be set to true will be the one used. For example,
if both *service.storage.google-storage* and *service.storage.amazon-storage* are set to true, the Google Bucket will be 
used. It is recommended that only one is set to true.   

The credentials, the key store and the trust store should be placed in the resources folder or its subfolders, since the 
paths to them are relative to this folder. For example *google.credentials.classpath = classpath:stores/BiologicalDataCredentials.json* 
means that the credentials file is inside the subfolder named *stores*.  

## License documentation

This project was decide to be open-source. When the code is published, any private information from 
*application.properties* and any private file should be omitted.

##  Developer documentation

This is section provides an overview of the code in case future additions are needed. 

Inside the *service* folder, there is an interface called *TextRecognitionService* which is the one that 
needs to be implemented in case an alternative to the Google Vision API is wanted. A configuration will also need to be created 
and added in the folder *configuration*. There is already an example on how to have multiple implementations of the same 
interface and only use one. The *StorageService* interface is implemented three times, each implementation has its own 
configuration and only one is instantiated at run-time. For more information, see this: 
[Conditionals on Beans](https://reflectoring.io/spring-boot-conditionals/).

The *controller* folder holds all the classes that deal with the http requests. For example 
*HelloController* has all the main html pages, while *ExtractTextController* communicates with a javascript 
to extract the text from an image.  

The html pages are inside the *resources/templates* folder and are augmented using Thymeleaf. This works well with Spring Boot 
and enables the creation of dynamic html pages. The images, css and javascript files are inside the *resources/static* folder.

The *repository* folder holds all the classes that are transformed into database tables. Modifying those will modify the 
columns of those tables, and adding more classes will create new tables.


