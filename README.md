# Odoo util

**Odoo util** is a **Spring Boot** library that provides a set of utilities to work with [**Odoo**](https://www.odoo.com/).

## Features
 - Easy authentication
 - Search for customers, sales and sales orders lines
 - Webhook API to receive notifications from Odoo (only for Sale order and sale order line implemented as of now)

## Requirements
- **Java 17** or higher
- **Spring Boot 3.0** or higher
- **Odoo** 17.0 or higher

## Installation

Add the Maven dependency to your project (pom.xml):

```xml
<dependency>
    <groupId>com.simexid</groupId>
    <artifactId>odoo-util</artifactId>
    <version>0.0.3</version>
</dependency>
```
For Gradle users, add this dependency to your build.gradle file:
```groovy
implementation 'org.simexid.keycloak:odoo-util:0.0.3'
```

## Configuration

Add the following properties to your application.properties file:

```yaml
simexid:
  crm:
    odoo-db: {your_odoo_db}
    odoo-api-url: {xmlrpc_odoo_endpoint} (ex. https://crm.simexid.org/xmlrpc/2/)
    odoo-username: {your_odoo_username}
    odoo-api-key: {your_odoo_api_key}
    api-keys: {comma separated list of api keys, used for webhook authentication}
    api-enabled: {true|false} for enabling/disabling the webhook api (default is false)
    api-path: {base path of webhook api} (dafault is "crm")
```
## Usage
You can extends the CrmServiceImpl class and override the methods you want to use. For example, to use the search method to search for customers, you can do the following:

```java

@Primary
@Service
public class OdooCRMServiceImpl extends CrmServiceImpl {
    @Autowired
    OdooCRMServiceImpl(XmlRpcClient xmlRpcClient) {
        super(xmlRpcClient);
    }

    @Override
    public void saveWebhook(String json) {
        // Implement your logic here, for example, save the json to a file
        System.out.println(json);
    }

    @Override
    public void giveAction(Object input) {
        // Implement your logic here, for example:
        
        //check if the input is a SaleOrderLineHook object or SaleOrderHook (as of now, only SaleOrderLineHook and SaleOrderHook are implemented)
        if (input instanceof SaleOrderLineHook) {
            SaleOrder saleOrder = (SaleOrder) input;
            System.out.println("Sale order: " + saleOrder.getName());
            switch (input.getProductId()) {
                case 21:
                    System.out.println("Product 21");
                    break;
                default:
                    break;
            }
        }
    }
}
```

and then:
```java
@Component
public class MyImplementation {
private final OdooCRMServiceImpl odooCRMService;

MyImplementation(OdooCRMServiceImpl odooCRMService) {
this.odooCRMService = odooCRMService;
}

CrmSearchEnum.SearchField field = CrmSearchEnum.SearchField.name;
CrmSearchEnum.SearchOperator criteria = CrmSearchEnum.SearchOperator.equal;
String search = "my@email.com;
List<Customer> = odooCRMService.getPartner(field, criteria, search, null);

}
```

The **Javadoc** is available [here](docs/apidocs/index.html).

## Rest API:

| HTTP Method | Endpoint                         | Input                                                           | Output                                                                                 | Description                                                  |
|-------------|----------------------------------|------------------------------------------------------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------|
| POST        | /webhooks/saleorder              | `SaleOrderHook` (in request body), `apikey` (parameter)           | 200 OK if successful, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Intercepts Odoo webhooks for a sale order                     |
| POST        | /webhooks/saleorderline          | `SaleOrderLineHook` (in request body), `apikey` (parameter)       | 200 OK if successful, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Intercepts Odoo webhooks for a sale order line                |
| GET         | /partners                        | `apikey` (parameter)                                              | 200 OK and list of `Customer`, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Retrieves a list of all partners/customers                    |
| GET         | /partner/{field}/{criteria}/{search} | `field` (path), `criteria` (path), `search` (path), `apikey` (parameter) | 200 OK and list of `Customer`, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Retrieves a list of partners/customers based on search criteria |
| GET         | /sales/{field}/{criteria}/{search} | `field` (path), `criteria` (path), `search` (path), `apikey` (parameter) | 200 OK and list of `SaleOrder`, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Retrieves a list of sales based on search criteria            |
| GET         | /fields/search/{model}           | `model` (path), `apikey` (parameter)                              | 200 OK and list of fields, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Retrieves a list of fields for a given model                  |
| GET         | /generic/search/{model}          | `model` (path), `apikey` (parameter)                              | 200 OK and generic object, 401 Unauthorized if unauthorized, 500 Internal Server Error if internal error | Retrieves a generic object for a given model                  |

## Author
- **Simexid** - [www.simexid.org](https://www.simexid.org) - [Pietro Saccone]
- **Email** - [info@simexid.org](mailto:info@simexid.org)

## Contributions
Contributions are welcome! If you have any suggestions or feature requests, please create an issue or a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
```
MIT License

Copyright (c) 2024 Pietro Saccone (Simexid)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
