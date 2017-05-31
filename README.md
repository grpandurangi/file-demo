# File Parser Application

Application responsible for processing dispute files and generate chargeback records.

## Running the API

### Build and Deploy

Generate sources and war
```bash
mvn clean package
```
```bash
mvn appengine:update
```

#### Debugging on local
```bash
mvn appengine:devserver
```

#### Acceptance Testing

Given the jar/war has already been generated:
```bash
execute mvn test on acceptance-tests folder 
```