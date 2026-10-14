# Weyra Finance Engine

Pure-Java primitives for money, tax, depreciation, and accounting periods.
No Spring. No JPA. No database. No multi-tenancy. Just the math.

## Purpose

This is a **library**, not a module. It provides the pure calculation layer
that Weyra's finance module (in the main `weyra` repository) will depend on.

The library knows:
- How to represent money and currency
- How to compute tax given a policy
- How to build a depreciation schedule
- What an accounting period is and when it's open/closed

The library does **not** know:
- Which tenant owns any record
- How to persist anything
- How to expose an HTTP endpoint
- How to authorize a user

Those concerns belong to the consuming module.

## Modules

- `core/money` — `Money`, `CurrencyUnit`
- `core/tax` — `TaxPolicy`, `DefaultTaxPolicy`, `TaxContext`, `TaxResult`
- `core/depreciation` — `DepreciationMethod`, `StraightLineDepreciation`, `DepreciationSchedule`
- `core/period` — `AccountingPeriod`
- `api` — public entry points (e.g. `FinanceEngine`)
- `spi` — interfaces for extension (custom tax policies, custom depreciation)
- `exception` — domain exceptions
- `util` — small helpers
- `version` — build/version metadata

## Build

```bash
mvn clean test      
