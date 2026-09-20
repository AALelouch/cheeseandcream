# Frontend specification: agent-scoped search

## Goal

Replace fixed, first-page lists in the Agent, Product, and Financial Operation interfaces with server-side, paginated search. Product and financial-operation searches are always scoped to the agent selected by the user.

All endpoints use the existing API base URL and authentication/API-key headers. Pagination follows Spring Data conventions:

- `page`: zero-based page index.
- `size`: requested page size.
- `sort`: optional sort expression, for example `name,asc`.

Paginated responses use the standard Spring `Page` JSON shape. Read records from `content`; use `number`, `size`, `totalElements`, `totalPages`, `first`, and `last` for paging state.

## Endpoint summary

| Use | Method | Endpoint | Search field | Agent-scoped |
|---|---|---|---|---|
| Search agents | `POST` | `/api/agents/search?page=0&size=10` | name, email, address, identification number | No |
| List agents that own products | `GET` | `/api/agents/with-products?page=0&size=10` | n/a | No |
| List an agent's products | `GET` | `/api/products/agent/{agentId}?page=0&size=10` | n/a | Yes |
| Search an agent's products | `POST` | `/api/products/agent/{agentId}/search?page=0&size=10` | product name | Yes |
| List an agent's operations | `GET` | `/api/financial-operations/agent/{agentId}?page=0&size=10` | n/a | Yes |
| Search an agent's operations | `POST` | `/api/financial-operations/agent/{agentId}/search?page=0&size=10` | concept | Yes |
| List every active agent | `GET` | `/api/agents?page=0&size=10` | n/a | No |

`GET /api/agents` remains available for product assignment and financial-operation creation. Do not replace it with `/with-products` in those forms.

## Search request

All three search endpoints use the same JSON structure:

```json
{
  "term": "cream"
}
```

Matching is case-insensitive and contains-based. An empty string returns the first matching page for the current scope; the frontend may use this to populate an autocomplete before the user types. Only active records are returned.

## Response examples

### Agent page

```json
{
  "content": [
    {
      "id": 12,
      "name": "North Farm",
      "email": "sales@northfarm.example",
      "phoneNumber": "5551234",
      "address": "Main Street",
      "balance": 1250.0,
      "identificationType": "2",
      "identificationNumber": "900123456"
    }
  ],
  "number": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

### Product page

```json
{
  "content": [
    {
      "id": 44,
      "name": "Double Cream",
      "quantity": 18.0,
      "price": 8.5,
      "cost": 5.0,
      "unitType": "UNIT",
      "categoryName": "Cream",
      "agentName": "North Farm"
    }
  ],
  "number": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

### Financial-operation page

```json
{
  "content": [
    {
      "id": 81,
      "productResponses": [],
      "idAgent": 12,
      "concept": "September invoice",
      "total": 250.0,
      "operationType": "SALE",
      "date": "2026-09-20T10:30:00"
    }
  ],
  "number": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

## Screen behavior

### Agents tab

1. Bind the search input to `POST /api/agents/search`.
2. Debounce typing by approximately 300 ms and ignore consecutive duplicate terms.
3. Reset `page` to `0` whenever the term changes.
4. Cancel the previous request when a newer term is emitted.
5. Use the returned page metadata for the table paginator.

### Products tab

1. Populate the tab's agent selector from `GET /api/agents/with-products`, loading more pages as needed. This selector intentionally excludes agents without active products.
2. Do not request products until an agent is selected.
3. With no product term, load `GET /api/products/agent/{agentId}`.
4. With a product term, call `POST /api/products/agent/{agentId}/search`.
5. Clear the current products, term, selection, and pagination whenever the agent changes.
6. Product create/edit forms must continue using `GET /api/agents`, because they need agents that do not yet own products.

### Financial Operation tab and creation bar

1. Replace the fixed list of 10 agents with an agent autocomplete backed by `POST /api/agents/search`.
2. Store the selected agent's numeric `id`, not its display name.
3. After selection, load operations with `GET /api/financial-operations/agent/{agentId}`; search concepts with `POST /api/financial-operations/agent/{agentId}/search`.
4. For a product-based operation, replace the fixed product list with an autocomplete backed by `POST /api/products/agent/{agentId}/search`.
5. Disable and clear the product selector until an agent is selected. Clear selected products if the agent changes.
6. Continue using `GET /api/agents` where a complete browseable agent list is required; use term search for interactive lookup.

## Suggested Angular service surface

```ts
searchAgents(term: string, page = 0, size = 10): Observable<Page<Agent>>
getAgentsWithProducts(page = 0, size = 10): Observable<Page<Agent>>
getProductsByAgent(agentId: number, page = 0, size = 10): Observable<Page<Product>>
searchProducts(agentId: number, term: string, page = 0, size = 10): Observable<Page<Product>>
getOperationsByAgent(agentId: number, page = 0, size = 10): Observable<Page<FinancialOperation>>
searchOperations(agentId: number, term: string, page = 0, size = 10): Observable<Page<FinancialOperation>>
```

URL-encode path and query values through Angular's `HttpParams`/router facilities; do not concatenate user-entered search text into URLs. The term belongs in the JSON body.

## Acceptance criteria

- Agent search is used by both the Agents tab and the Financial Operation agent selector.
- No selector silently limits the available agents or products to a fixed first 10 records; pagination or incremental loading is available.
- Product-name results contain only products owned by the selected agent.
- Concept results contain only financial operations owned by the selected agent.
- The Products tab lists only agents with at least one active product.
- Product assignment and financial-operation creation can still access all active agents.
- Changing the selected agent clears stale products, operations, terms, and page indexes.
- Empty, loading, error, and no-results states are presented independently for each autocomplete/table.
