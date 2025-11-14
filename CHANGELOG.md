# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.0.5] - 2025-11-14

### Changed

Dependency updates:

* Upgraded `spring-boot-starter`, `spring-boot-starter-web`, `spring-boot-autoconfigure-processor`, and `spring-boot-starter-test` dependencies from version `3.3.4` to `3.5.7`.
* Upgraded `gson` dependency from `2.10.1` to `2.13.2`.

Build and test plugin updates:

* Updated `maven-compiler-plugin` from `3.13.0` to `3.14.1`, and `maven-surefire-plugin` from `2.22.2` to `3.5.4` (with added configuration for JUnit platform compatibility).
* Upgraded `jacoco-maven-plugin` from `0.8.10` to `0.8.14` and `maven-javadoc-plugin` from `3.10.1` to `3.12.0`.

Publishing and signing plugin updates:

* Updated `maven-gpg-plugin` from `3.2.7` to `3.2.8` and `central-publishing-maven-plugin` from `0.6.0` to `0.9.0`.


## [0.0.4] - 2025-11-06

### Added
- **JSON-RPC Support**: Implemented complete JSON-RPC connector (`CrmJsonRpcConnector`)
  - `crmCall(String path, String method, Map<String, Object> params)` - Generic JSON-RPC calls
  - `crmCallExecuteKw(String model, String methodName, Object[] args, Map<String, Object> kwargs)` - Convenience method for Odoo's `execute_kw` via JSON-RPC
  - Provides an alternative to XML-RPC for applications that prefer JSON-RPC communication
- **Enhanced REST Authentication**: Controller now supports both authentication methods
  - Query parameter: `?apikey=your-key`
  - HTTP Authorization header: `Authorization: Bearer your-key`
- **New Webhook Endpoints**:
  - `POST /{api-path}/webhooks/partner` - For customer/partner webhooks
  - `POST /{api-path}/webhooks/generic` - For any generic Odoo object webhooks
- **Improved Error Handling and Logging**:
  - Added `safeExecute()` wrapper in `CrmServiceImpl` for centralized XML-RPC error handling
  - Integrated SLF4J logging for better debugging and error tracking
- **Comprehensive Test Suite**:
  - Added unit tests for all service methods
  - Added tests for controller endpoints including Authorization header handling
  - Added tests for JSON-RPC connector (success and failure scenarios)
  - Added tests for model deserializers
  - Configured JaCoCo for code coverage reporting
  - Test coverage exceeds 80%

### Fixed
- **Configuration Property Binding**: Fixed typo in `SimexidOdooConfigurationProperties`
  - Renamed field from `apyKeys` to `apiKeys` to correctly bind to `simexid.crm.api-keys` property
  - Updated getters and setters accordingly
- **API Key Validation**: Improved security in `CrmController`
  - Changed from checking only first element to using `anyMatch()` for validating against all configured API keys
  - Properly handles null `apiKeys` array to prevent NullPointerException

### Changed
- **Controller Method Signatures**: All REST endpoints now accept optional `Authorization` header parameter alongside existing `apikey` query parameter
- **Documentation**: Completely overhauled README.md with:
  - Complete configuration properties documentation with descriptions and examples
  - All REST API endpoints with both authentication methods documented
  - Comprehensive service methods documentation
  - JSON-RPC connector usage examples
  - All available enums and their values
  - Usage examples for common scenarios
  - Migration notes for upgrading from previous versions

### Technical Details
- **Dependencies**: No new dependencies added; existing dependencies used more effectively
- **Backward Compatibility**: All changes are backward compatible except for the configuration property name fix (`api-keys` vs `apyKeys`)
- **Migration Required**: Users upgrading from 0.0.3 must verify their configuration uses `simexid.crm.api-keys` (not `apyKeys`)

### Testing
To run tests and generate coverage report:
```bash
mvn clean test jacoco:report
```
Coverage report available at: `target/site/jacoco/index.html`

## [0.0.3] - Previous Release
Previous stable release.

---

For complete documentation, see [README.md](README.md).