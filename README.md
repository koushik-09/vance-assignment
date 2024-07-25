# Scraping Data From Yahoo Finance Website

A Spring boot Application to scrape historical exchange data from yahoo finance website.

## Requirements
- Java 8 or higer (java-21 used in app)
- Maven installed
- Postman

## Getting Started


1. #### Clone the repository:

   ```bash
   git clone https://github.com/koushik-09/vance-assignment.git
   ```
2. #### Import folder into your IDE
3. #### Run the Application

## Usage
1. Run the Application, it runs on default port number 8080

2. You can access the application using
####
  ```bash
localhost:8080/api/hello
```
3. App is configured with In-memory database which can be accessed using
####
```
localhost:8080/h2-console
```
**Credential to access database:**

**url** : jdbc:h2:mem:testdb

**username** : admin

**password** : admin










## API Reference

**Total API's :**

#### 1. api/hello
#### 2. api/forex-data
####

```
GET api/hello
```
Returns hello World! ,Used to check if application is running status

```
POST api/forex-data
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `from`      | `string` | **Required** From Currency Ex: USD,EUR |
| `to`|`string`|**Required** To Currency EX: INR, AED|
|`period`|`string`|**Required** Time Period(1W,1M,3M,6M,1Y)|

Returns Exchange Rates of From Currency to To Currency between specified time period.
For example, if you want exchange data of USD to INR of past 2 weeks, from will be USD and to will be INR and period will be 2W.

## Features
Scrapes historical exchange data

The application uses cron jobs to scrape data periodically:

- Every 1 week: 0 0 * * 0
- Every 1 month: 0 0 1 * *
- Every 6 months: 0 0 1 */6 *
- Every 1 year: 0 0 1 1 *

## Hosting details

The application is hosted on render and can be accessed using
```
https://vance-assignment.onrender.com/api
```
**If server sends no response or does not load, please wait for 1-2 minutes and try again as render takes down service when it is inactive.**

