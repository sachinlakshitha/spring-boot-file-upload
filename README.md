# Spring Boot File Upload Example

This is a simple Spring Boot application that demonstrates how to upload, download and delete files to the server.

## Requirements

1. Java 17
2. Maven 3

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/sachinlakshitha/spring-boot-file-upload.git
```

**2. Change file upload location**

Open `src/main/resources/application.properties` file and change the `file.upload.path` property to the path where you want to store the uploaded files.

**3. Build and run the app using maven**

```bash
mvn package
java -jar target/spring-boot-file-upload-1.0.0-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Example of Requests

### Upload File

    POST /api/file/upload

    curl -F "file=@/path/to/file" http://localhost:8080/api/file/upload

### Download File

    GET /api/file/download/{fileName}

    curl -X GET http://localhost:8080/api/file/download/{fileName}

### Delete File

    DELETE /api/file/{fileName}

    curl -X DELETE http://localhost:8080/api/file/{fileName}

### Delete Folder

    DELETE /api/folder/{folderName}

    curl -X DELETE http://localhost:8080/api/folder/{folderName}
