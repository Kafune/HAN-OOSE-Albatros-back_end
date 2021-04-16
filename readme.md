<h1 align="center">
  <br>
  Back-end Run Connect Ede
  <br>
</h1>
Beschrijving

## table of contents
1. [Authors](#authors)
2. [Software Guidebook](#software-guidebook)
3. [API](#API)<br>
    3.1 [Methods used](#Methods-used)<br>
    3.2 [Response codes](#Response-codes)<br>
    3.3 [Endpoints](#Endpoints)<br>
        3.3.1 [Login](#Login)<br>

        

## Authors
* **R. Boudewijn** - [nxttx](https://github.com/nxttx)

## Software guidebook
[Click Here](/software-guidebook/readme.md)

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

#### Login

```
url:    /login 
method: POST
```

It will perform a request with an object in the body of the form

```
{
  "user":     "meron", 
  "password": "MySuperSecretPassword12341"
}
```

It will expect a response containing an object of the form

```
{
  "token":  "1234-1234-1234", 
  "user":   "Meron Brouwer"
}
```

This token is then stored in LocalStorage and used for each following
request.
