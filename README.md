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
    <version>0.0.4</version>
</dependency>
```
For Gradle users, add this dependency to your build.gradle file:
```groovy
implementation 'org.simexid.keycloak:odoo-util:0.0.4'
```

## Configuration

Add the following properties to your `application.yml` or `application.properties` file:

### All Available Configuration Properties

```yaml
simexid:
  crm:
    # Required: Odoo database name
    odoo-db: {your_odoo_db}
    
    # Required: XML-RPC endpoint URL (include /xmlrpc/2/ path)
    odoo-api-url: {xmlrpc_odoo_endpoint}  # Example: https://crm.example.org/xmlrpc/2/
    
    # Optional: JSON-RPC endpoint URL (if using JSON-RPC connector)
    odoo-json-url: {jsonrpc_odoo_endpoint}  # Example: https://crm.example.org
    
    # Required: Odoo username for authentication
    odoo-username: {your_odoo_username}
    
    # Required: Odoo API key (generated from user profile page)
    odoo-api-key: {your_odoo_api_key}
    
    # Required for REST API: Array of API keys for webhook/REST authentication
    api-keys: {comma_separated_list_of_api_keys}  # Example: key1,key2,key3
    
    # Optional: Enable/disable the REST controller (default: false)
    api-enabled: true  # Set to true to enable REST endpoints
    
    # Optional: Base path for REST API endpoints (default: "crm")
    api-path: crm  # The REST endpoints will be available at /{api-path}/...
```

### Configuration Notes

- **odoo-api-url**: Must include the `/xmlrpc/2/` path for XML-RPC calls
- **odoo-json-url**: Base URL without path, used by `CrmJsonRpcConnector` for JSON-RPC calls
- **api-keys**: Accepts multiple keys as comma-separated values; used for authentication via query parameter or Authorization header
- **api-enabled**: Must be set to `true` to enable the REST controller endpoints
- **api-path**: Defines the base path for all REST endpoints (e.g., `crm` makes endpoints available at `/crm/partners`, `/crm/webhooks/saleorder`, etc.)
## Usage

### Extending CrmServiceImpl

You can extend the `CrmServiceImpl` class and override the webhook handler methods to implement custom behavior:

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
        // Implement your logic here, for example, save the json to a database
        System.out.println("Received webhook: " + json);
        // Save to database, send notification, etc.
    }

    @Override
    public void giveAction(Object input) {
        // Implement your logic here based on webhook type
        
        if (input instanceof SaleOrderHook) {
            SaleOrderHook saleOrderHook = (SaleOrderHook) input;
            System.out.println("Sale order webhook: " + saleOrderHook.getName());
            // Perform actions based on sale order
        }
        
        if (input instanceof SaleOrderLineHook) {
            SaleOrderLineHook lineHook = (SaleOrderLineHook) input;
            System.out.println("Sale order line webhook");
            // Check product and perform actions
            switch (lineHook.getProductId()) {
                case 21:
                    System.out.println("Special handling for product 21");
                    break;
                default:
                    System.out.println("Standard product handling");
                    break;
            }
        }
        
        if (input instanceof CustomerHook) {
            CustomerHook customerHook = (CustomerHook) input;
            System.out.println("Customer webhook: " + customerHook.getName());
            // Sync customer data, trigger workflows, etc.
        }
    }
}
```

### Using the Service in Your Application

```java
@Component
public class MyOdooIntegration {
    
    private final CrmServiceImpl crmService;

    @Autowired
    public MyOdooIntegration(CrmServiceImpl crmService) {
        this.crmService = crmService;
    }

    public void searchCustomerByEmail() {
        try {
            CrmSearchEnum.SearchField field = CrmSearchEnum.SearchField.email;
            CrmSearchEnum.SearchOperator criteria = CrmSearchEnum.SearchOperator.equal;
            String search = "customer@example.com";
            
            List<Customer> customers = crmService.getPartner(field, criteria, search, null);
            
            customers.forEach(customer -> {
                System.out.println("Found customer: " + customer.getName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Phone: " + customer.getPhone());
            });
        } catch (Exception e) {
            System.err.println("Error searching customer: " + e.getMessage());
        }
    }
    
    public void getSalesOrders() {
        try {
            CrmSearchEnum.SearchField field = CrmSearchEnum.SearchField.partnerid;
            CrmSearchEnum.SearchOperator criteria = CrmSearchEnum.SearchOperator.equal;
            Integer partnerId = 123;
            
            List<SaleOrder> orders = crmService.getSales(field, criteria, partnerId, null);
            
            orders.forEach(order -> {
                System.out.println("Order: " + order.getName());
                System.out.println("Total: " + order.getAmountTotal());
                System.out.println("State: " + order.getState());
                
                // Access order lines
                order.getOrderLineObject().forEach(line -> {
                    System.out.println("  - " + line.getName() + ": " + line.getPriceTotal());
                });
            });
        } catch (Exception e) {
            System.err.println("Error fetching sales orders: " + e.getMessage());
        }
    }
}
```

### Working with Custom Fields

You can specify custom fields to retrieve:

```java
// Get partners with specific fields
Object[] customFields = new Object[]{"name", "email", "phone", "vat", "website", "comment"};
List<Customer> partners = crmService.getAllPartners(customFields);

// Get sale orders with custom fields
Object[] orderFields = new Object[]{"name", "date_order", "amount_total", "state", "user_id", "team_id"};
List<SaleOrder> orders = crmService.getSales(
    CrmSearchEnum.SearchField.id,
    CrmSearchEnum.SearchOperator.greater_than,
    1000,
    orderFields
);
```

## Notes for v0.0.4

The 0.0.4 development updates include important fixes and improvements:

### Configuration Property Fix
- The configuration property for API keys is `simexid.crm.api-keys`. A previous typo (`apyKeys`) has been fixed.
- **Migration**: Verify your `application.yml`/`application.properties` uses `simexid.crm.api-keys`.

### Enhanced REST Authentication
- The webhook/search controller now accepts **both**:
  - Query parameter: `?apikey=your-key`
  - HTTP header: `Authorization: Bearer your-key`
- Both methods are checked and validated against the configured `api-keys` list.

### JSON-RPC Support
- A JSON-RPC connector (`CrmJsonRpcConnector`) has been implemented with the following methods:
  - `crmCall(...)` - Generic JSON-RPC calls
  - `crmCallExecuteKw(...)` - Convenience method for Odoo's `execute_kw`
- This provides an alternative to XML-RPC for applications that prefer JSON-RPC.
- XML-RPC remains fully supported via `CrmXmlRpcConnector` and `CrmServiceImpl`.

### Improved Logging and Error Handling
- `CrmServiceImpl` now uses a `safeExecute(...)` wrapper with SLF4J logging for all external RPC calls.
- Errors are logged with full context to aid debugging.

### Testing and Coverage
- Comprehensive unit tests have been added for:
  - All service methods
  - Controller endpoints (including Authorization header handling)
  - JSON-RPC connector
  - Model deserializers
  - Configuration properties
- To run tests and generate coverage reports:

```powershell
mvn clean test jacoco:report
```

- The HTML coverage report will be available at `target/site/jacoco/index.html` after the build.

### Additional Webhook Endpoints
- Added `/webhooks/partner` endpoint for customer/partner webhooks
- Added `/webhooks/generic` endpoint for any generic Odoo object webhooks

The **Javadoc** is available [here](docs/apidocs/index.html).

## Available Methods and APIs

### CrmServiceImpl Methods

The `CrmServiceImpl` service provides the following methods for interacting with Odoo via XML-RPC:

#### Authentication
- `int authenticate()` - Authenticates with Odoo and returns the user ID. Authentication is cached for 2 hours.

#### Partner/Customer Operations
- `List<Customer> getAllPartners(Object[] fields)` - Retrieves all partners/customers. Pass `null` for fields to use defaults.
- `List<Customer> getPartner(SearchField searchField, SearchOperator searchOperator, Object search, Object[] fields)` - Retrieves partners based on search criteria.

#### Sales Order Operations
- `List<SaleOrder> getSales(SearchField searchField, SearchOperator searchOperator, Object search, Object[] fields)` - Retrieves sale orders based on search criteria.
- `List<SaleOrderLine> getOrderLine(List<Integer> orderLineIds)` - Retrieves sale order lines by their IDs.

#### Generic Operations
- `Object getFields(CrmSearchEnum.Model model)` - Retrieves field definitions for a given Odoo model.
- `Object getGenericObject(CrmSearchEnum.Model model)` - Retrieves all records for a given Odoo model.

#### Webhook Handlers (Override in Your Implementation)
- `void saveWebhook(String json)` - Called when a webhook is received. Default implementation is no-op; override to save webhook data.
- `void giveAction(Object input)` - Called to perform actions based on webhook input. Default implementation is no-op; override to implement custom logic.

### CrmJsonRpcConnector Methods

The `CrmJsonRpcConnector` provides JSON-RPC connectivity as an alternative to XML-RPC:

#### Authentication
- `int authenticate()` - Authenticates via JSON-RPC and returns the user ID. Authentication is cached for 2 hours.

#### JSON-RPC Calls
- `Object crmCall()` - Performs a generic JSON-RPC call with empty parameters.
- `Object crmCall(String path, String method, Map<String, Object> params)` - Performs a JSON-RPC call with custom path, method, and parameters.
- `Object crmCallExecuteKw(String model, String methodName, Object[] args, Map<String, Object> kwargs)` - Convenience method to call Odoo's `execute_kw` via JSON-RPC.

**Note:** To use `CrmJsonRpcConnector`, you must configure `simexid.crm.odoo-json-url` in your properties.

### Available Enums

#### CrmSearchEnum.SearchField
Search fields for querying Odoo:
- `name` - Name field
- `email` - Email field
- `phone` - Phone field
- `partnerid` - Partner ID field
- `partnerinvoiceid` - Partner invoice ID field
- `partnershippingid` - Partner shipping ID field
- `orderid` - Order ID field
- `id` - Generic ID field

#### CrmSearchEnum.SearchOperator
Search operators for queries:
- `equal` - Exact match (=)
- `like` - Case-insensitive partial match (ilike)
- `not_equal` - Not equal (!=)
- `greater_than` - Greater than (>)
- `less_than` - Less than (<)

#### CrmSearchEnum.Model
Available Odoo models:
- `partner` - res.partner
- `saleorder` - sale.order
- `saleorderline` - sale.order.line
- `accountmove` - account.move
- `accountpayment` - account.payment
- `accountjournal` - account.journal
- `accounttax` - account.tax
- `purchaseorder` - purchase.order
- `purchaseorderline` - purchase.order.line
- `projecttask` - project.task
- `project` - project.project
- `paymenttranasction` - payment.transaction
- `crmlead` - crm.lead
- `salereport` - sale.report

#### CrmSearchEnum.State
Sale order states:
- `draft` - Draft
- `sent` - Quotation Sent
- `sale` - Sales Order
- `done` - Locked
- `cancel` - Cancelled

### Usage Examples

#### Using Service Methods Directly

```java
@Autowired
private CrmServiceImpl crmService;

// Get all partners
List<Customer> allPartners = crmService.getAllPartners(null);

// Search for a specific partner by email
List<Customer> customers = crmService.getPartner(
    CrmSearchEnum.SearchField.email,
    CrmSearchEnum.SearchOperator.equal,
    "customer@example.com",
    null
);

// Get sale orders by partner ID
List<SaleOrder> orders = crmService.getSales(
    CrmSearchEnum.SearchField.partnerid,
    CrmSearchEnum.SearchOperator.equal,
    123,
    null
);

// Get fields for a model
Object fields = crmService.getFields(CrmSearchEnum.Model.partner);
```

#### Using JSON-RPC Connector

```java
@Autowired
private CrmJsonRpcConnector jsonRpcConnector;

// Authenticate
int uid = jsonRpcConnector.authenticate();

// Execute a custom JSON-RPC call
Map<String, Object> params = new HashMap<>();
params.put("service", "object");
params.put("method", "execute");
Object result = jsonRpcConnector.crmCall("/jsonrpc", "call", params);

// Use execute_kw convenience method
Map<String, Object> kwargs = new HashMap<>();
kwargs.put("fields", new String[]{"name", "email"});
Object partners = jsonRpcConnector.crmCallExecuteKw(
    "res.partner",
    "search_read",
    new Object[]{},
    kwargs
);
```

## Rest API:

All REST endpoints support authentication via:
- **Query parameter**: `?apikey={your_api_key}`
- **HTTP Header**: `Authorization: Bearer {your_api_key}`

### Webhook Endpoints

| HTTP Method | Endpoint                         | Input                                                           | Output                                                                                 | Description                                                  |
|-------------|----------------------------------|------------------------------------------------------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------|
| POST        | /{api-path}/webhooks/saleorder   | `SaleOrderHook` (body), `apikey` (param) or `Authorization` (header) | 200 OK / 401 Unauthorized / 500 Internal Server Error | Intercepts Odoo webhooks for a sale order                     |
| POST        | /{api-path}/webhooks/saleorderline | `SaleOrderLineHook` (body), `apikey` (param) or `Authorization` (header) | 200 OK / 401 Unauthorized / 500 Internal Server Error | Intercepts Odoo webhooks for a sale order line                |
| POST        | /{api-path}/webhooks/partner     | `CustomerHook` (body), `apikey` (param) or `Authorization` (header) | 200 OK / 401 Unauthorized / 500 Internal Server Error | Intercepts Odoo webhooks for a customer/partner               |
| POST        | /{api-path}/webhooks/generic     | `Object` (body), `apikey` (param) or `Authorization` (header) | 200 OK / 401 Unauthorized / 500 Internal Server Error | Intercepts Odoo webhooks for any generic object               |

### Query Endpoints

| HTTP Method | Endpoint                         | Input                                                           | Output                                                                                 | Description                                                  |
|-------------|----------------------------------|------------------------------------------------------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------|
| GET         | /{api-path}/partners             | `apikey` (param) or `Authorization` (header)                    | 200 OK + `List<Customer>` / 401 Unauthorized / 500 Internal Server Error | Retrieves all partners/customers                              |
| GET         | /{api-path}/partner/{field}/{criteria}/{search} | `field` (path), `criteria` (path), `search` (path), `apikey` (param) or `Authorization` (header) | 200 OK + `List<Customer>` / 401 Unauthorized / 500 Internal Server Error | Retrieves partners/customers based on search criteria         |
| GET         | /{api-path}/sales/{field}/{criteria}/{search} | `field` (path), `criteria` (path), `search` (path), `apikey` (param) or `Authorization` (header) | 200 OK + `List<SaleOrder>` / 401 Unauthorized / 500 Internal Server Error | Retrieves sales orders based on search criteria               |
| GET         | /{api-path}/fields/search/{model} | `model` (path), `apikey` (param) or `Authorization` (header)    | 200 OK + field list / 401 Unauthorized / 500 Internal Server Error | Retrieves all fields for a given Odoo model                   |
| GET         | /{api-path}/generic/search/{model} | `model` (path), `apikey` (param) or `Authorization` (header)   | 200 OK + generic object / 401 Unauthorized / 500 Internal Server Error | Retrieves all records for a given Odoo model                  |

### Path Parameters

- **{field}**: Search field enum value (e.g., `name`, `email`, `phone`, `id`, `partnerid`)
- **{criteria}**: Search operator enum value (e.g., `equal`, `like`, `not_equal`, `greater_than`, `less_than`)
- **{search}**: Search value (string or numeric)
- **{model}**: Odoo model enum value (e.g., `partner`, `saleorder`, `saleorderline`, `accountmove`, `project`, `crmlead`, etc.)

### Authentication Examples

**Using query parameter:**
```bash
curl "https://your-app.com/crm/partners?apikey=your-api-key-here"
```

**Using Authorization header:**
```bash
curl -H "Authorization: Bearer your-api-key-here" "https://your-app.com/crm/partners"
```

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
