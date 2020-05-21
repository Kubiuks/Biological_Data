# Development testing


Development testing is measured primarily against the “Basic user” user story, which includes the core requirements of the product such as the web interface and a backend to store images to be organised and accessed. 




The components of the application include the user interface, optical character recognition service, image storage and image database. The testing methodology for these is as follows:

### User Interface:
    
    The user interface is the hardest thing to test since a "sucessful" user interface is a subjective measurement. Testing an interface is also not really feasible to automate unlike tests for most other parts of the application. Methodology for testing a user interface therefore consists of taking note of feedback from a user who is not afiliated with the development team to ensure impartiality. Changes can then be made based on how the user perceives the interface in general. Aside from this there are also basic objectives which have been checked to ensure that the indended functionality is accessible. This includes attempting the following tasks from a user's perspective:
        - Logging into the application.
        - Uploading a picture to the Application's storage
        - Extracting the text from the uploaded image
        - Viewing the gallery of all uploaded images
        - Logging out
    
    This part of testing will be judged a sucess if all of these objectives can be completed and the user feels that using the program is pleasant with no hinderances to the expected functionality.

### Optical character recognition:

    Optical character recognition can be tested objectively and automatically with springboot unit tests. These unit tests cover a range of simple to advanced procedures detailed here:
        -Making a connection to the google cloud vision service
        -Sending an image to the cloud vision service and receiving a response
        -sending a printed text image and comparing the response against a human transcription of the text. (pass if the strings match)
        -sending an image of some handwiting and comparing the response against a transcription (pass if the strings match)

### Image storage:

    
    Testing the image storage is simple through unit tests, a file is written to the storage and is then read back in. The test will fail if there are any permissions errors or lack of storage space. This test can be extended by restarting the application server between storing the file and reading it to confirm that the storage is persistent.

### Image database:
    The image database is another simple component to test with unit tests. It is a flat file database so writing a single record to the database and confirming that it can be retreived through a query is enough to confirm the database is working as intended.


All unit tests will be run automatically through a pipeline on bitbucket and will notify the repository members if any tests fail.


