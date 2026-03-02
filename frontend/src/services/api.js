import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

// Agents API
export const agentApi = {
  getAll: () => api.get('/agents'),
  getById: (id) => api.get(`/agents/${id}`),
  create: (data) => api.post('/agents', data),
  update: (id, data) => api.put(`/agents/${id}`, data),
  delete: (id) => api.delete(`/agents/${id}`)
}

// Products API
export const productApi = {
  getAll: () => api.get('/products'),
  getById: (id) => api.get(`/products/${id}`),
  create: (data) => api.post('/products', data),
  update: (id, data) => api.put(`/products/${id}`, data),
  delete: (id) => api.delete(`/products/${id}`)
}

// Invoices API
export const invoiceApi = {
  getAll: () => api.get('/invoices'),
  getById: (id) => api.get(`/invoices/${id}`),
  getByAgentId: (agentId) => api.get(`/invoices/agent/${agentId}`),
  create: (data) => api.post('/invoices', data),
  update: (id, data) => api.put(`/invoices/${id}`, data),
  delete: (id) => api.delete(`/invoices/${id}`)
}

// Dashboard API
export const dashboardApi = {
  getAll: () => api.get('/dashboard'),
  getByDateRange: (startTime, endTime) => api.post('/dashboard/by-date-range', { startTime, endTime }),
  getTotalDebt: () => api.get('/dashboard/total-debt'),
  getTotalDebtByDateRange: (startTime, endTime) => api.post('/dashboard/total-debt/by-date-range', { startTime, endTime }),
  getTotalRevenue: () => api.get('/dashboard/total-revenue'),
  getTotalRevenueByDateRange: (startTime, endTime) => api.post('/dashboard/total-revenue/by-date-range', { startTime, endTime }),
  getTotalProfit: () => api.get('/dashboard/total-profit'),
  getTotalProfitByDateRange: (startTime, endTime) => api.post('/dashboard/total-profit/by-date-range', { startTime, endTime })
}

export default api


