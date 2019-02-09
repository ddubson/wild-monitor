# WildMonitor

[![Build Status](https://travis-ci.org/ddubson/wild-monitor.svg?branch=master)](https://travis-ci.org/ddubson/wild-monitor)

`Job` is an instance of a run that can have the following statuses:

- `PENDING`
- `STARTED`
- `FAILED`
- `STOPPED`
- `SUCCEEDED`
- `EXPIRED`

Default status at creation of a new job is `PENDING`

`Project` is a set of jobs with a unique project key as an identifier

## API Guides

Sample request/responses use super nifty [httpie tool](https://httpie.org/)

### Create New Project

Request (sample):
```
http POST :8080/projects 'projectName=my project'
```

Response (sample):
```json
{
    "id": "bb199098-b270-4bc0-9baa-aac922615d8a",
    "projectKey": "473e185500afecac3eb5bddd06b38a4382905d86e42eef5847111207af3b4b638005224305fa6378c80da57535cddd97bfd05d7f90118c5c31f603a7d5668787",
    "projectName": "my project"
}
```

### Create a job within a project

Request (sample)

```bash
http POST :8080/jobs 'projectKey=473e185500afecac3eb5bddd06b38a4382905d86e42eef5847111207af3b4b638005224305fa6378c80da57535cddd97bfd05d7f90118c5c31f603a7d5668787'
```

Response (sample)

```bash
{
    "id": "2a7f1c57-d420-472a-84b5-8034c3b199f6",
    "jobStatus": "PENDING",
    "projectKey": "473e185500afecac3eb5bddd06b38a4382905d86e42eef5847111207af3b4b638005224305fa6378c80da57535cddd97bfd05d7f90118c5c31f603a7d5668787"
}
```

### Get job details

Request (sample)

```bash
http GET :8080/jobs/2a7f1c57-d420-472a-84b5-8034c3b199f6
```

Response (sample)

```json
{
    "id": "2a7f1c57-d420-472a-84b5-8034c3b199f6",
    "jobStatus": "PENDING",
    "projectKey": "473e185500afecac3eb5bddd06b38a4382905d86e42eef5847111207af3b4b638005224305fa6378c80da57535cddd97bfd05d7f90118c5c31f603a7d5668787"
}
```