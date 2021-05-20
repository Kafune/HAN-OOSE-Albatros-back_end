<h1 align="center">
  <br>
  Back-end Run Connect Ede
  <br>
</h1>
Beschrijving

## table of contents
1. [Authors](#authors)
2. [setup](#setup)
3. [API](#API)<br>
    3.1 [Methods used](#Methods-used)<br>
    3.2 [Response codes](#Response-codes)<br>
    3.3 [Endpoints](#Endpoints)<br>
        3.3.1 [Login](#Login)<br>

        

## Authors
* **R. Boudewijn** - [nxttx](https://github.com/nxttx)
* **M. Yasin**
* **K. Li** - [Kafune](https://github.com/Kafune)

## setup
To get the database working run the following scripts:
1. DATABASE_DDL
2. DATABASE_DML
3. DATABASE_SP

## API

In general the API must conform the standards of a RESTFull API. It will use HTTP methods and expects HTTP statuscodes in its response.

### Methods used

* GET : In case of acquiring one, or multiple resources.
* POST: In case of creating a resource.
* PUT: In case of modifying  a resource.
* DELETE: In case of deleting a resource.

### Response codes

The client will expect the following respond codes to be used

* 200: OK. A response to a successful GET, PUT or DELETE.
* 201: Resource has been created. A response to a successful POST.
* 400: Bad Request. Something is wrong with the request. This could be due to
  a missing query-parameter for the token.
* 401: Unauthorized. Authorization has failed. This can happen if the user tried to log in, but supplied an invalid username/password.
* 403: Forbidden. The request was valid, but you have requested a resource for which are not authorized. This will probably mean you have provided a token that is invalid.
* 404: Not found. You have requested an endpoint that is not available.

### Endpoints
The following endpoints are implemented

#### helloworld

```
url:    /hello
method: get
```

It will expect a response containing an object of the form

```
{
  "text": "hello world!",
  "time": "1619082760084"
}
```

#### Routes

Adds a route.
```
url:    /routes
method: POST
```


It will expect a body containing an object of the form

```
{
  "name": "BosWandeling",
  "routeId": 1,
  "distance": 5,
  "segments": [
    {
      "id": 5,
      "startCoordinate": {
        "longitude": 12,
        "latitude": 45,
        "altitude": 0
      },
      "endCoordinate": {
        "longitude": 13,
        "latitude": 45.1,
        "altitude": -2
      },
      "poi": {
        "id": 5,
        "name": "Kerk",
        "description": "Een mooie middeleeuwse kerk."
      }
    },
    {
      "id": 6,
      "startCoordinate": {
        "longitude": 13,
        "latitude": 45.1,
        "altitude": -2
      },
      "endCoordinate": {
        "longitude": 14,
        "latitude": 44,
        "altitude": 3
      }
    }
  ]
}
```

Get all routes.
```
url:    /routes
method: GET
```

It will perform a body containing a complete list of routes

```
[
    {
        "description": "Grote kerk",
        "distance": 10,
        "name": "KerkRondje",
        "routeId": 1,
        "segments": []
    },
    {
        "description": "Restaurant",
        "distance": 7,
        "name": "Rival Foods",
        "routeId": 2,
        "segments": []
    }
]
```
Get all segments that belong to a route.
```
url:    /segments/:id
method: GET
```

It will perform a body containing a complete list of segments that belong to a route

```
[
    {
        "endCoordinate": {
            "altitude": 27.0,
            "latitude": 52.030033,
            "longitude": 5.675282
        },
        "id": 1,
        "poi": {
            "description": "Kerk",
            "id": 0,
            "name": "Connectkerk"
        },
        "startCoordinate": {
            "altitude": 28.0,
            "latitude": 52.030944,
            "longitude": 5.674306
        }
    },
    {
        "endCoordinate": {
            "altitude": 26.0,
            "latitude": 52.030257,
            "longitude": 5.679166
        },
        "id": 2,
        "poi": {
            "id": 0
        },
        "startCoordinate": {
            "altitude": 27.0,
            "latitude": 52.030033,
            "longitude": 5.675282
        }
    },
    {
        "endCoordinate": {
            "altitude": 27.0,
            "latitude": 52.034237,
            "longitude": 5.679981
        },
        "id": 3,
        "poi": {
            "id": 0
        },
        "startCoordinate": {
            "altitude": 26.0,
            "latitude": 52.030257,
            "longitude": 5.679166
        }
    }
]
```
#### Registration
```
url:    /registration
method: POST
```


It will expect a body containing an object of the form

```
{ 
  "firstName": "Mo",
  "lastName": "Yasin",
  "emailAddress": "ags@",
  "username": "Mohammad Yasin",
  "googleId": "sdw3232dsssdsd",
  "imageUrl": "url/fotos"
}
```
It will perform a body containing a complete object of the form

```
{
    "imageUrl": "url/fotos",
    "emailAddress": "ags@",
    "firstName": "Mo",
    "googleId": "sdw3232dsssdsd",
    "lastName": "Yasin",
    "totalScore": 0,
    "userId": 10,
    "username": "Mohammad Yasin"
}
```
#### Activities
Add a new activity
```
url:    /activities
method: POST
```


It will expect a body containing an object of the form

```
{
    "routeId": 2,
    "userId": 32,
    "point": 32,
    "duration": 12,
    "distance": 23,
    "segments": [{
                "endCoordinate": { 
                    "longitude": 5.679981, 
                    "latitude": 52.034237, 
                    "altitude": 27
                },
                "startCoordinate": {
                    "longitude": 5.679166, 
                    "latitude": 52.030257, 
                    "altitude": 26.0
                },
                "id": 2
        },         
        {
                "endCoordinate": { 
                    "longitude": 5.3232, 
                    "latitude": 52.565, 
                    "altitude": 21
                },
                "startCoordinate": {
                    "longitude": 5.55, 
                    "latitude": 52.66, 
                    "altitude": 28.0
                },
                "id": 4
        }]
}
routeId mag null zijn
```
