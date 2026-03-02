import { Routes, Route } from 'react-router-dom'
import Layout from './components/Layout'
import Dashboard from './pages/Dashboard'
import Agents from './pages/Agents'
import Products from './pages/Products'
import Invoices from './pages/Invoices'
import NewInvoice from './pages/NewInvoice'
import EditInvoice from './pages/EditInvoice'
import './App.css'

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Dashboard />} />
        <Route path="agents" element={<Agents />} />
        <Route path="products" element={<Products />} />
        <Route path="invoices" element={<Invoices />} />
        <Route path="invoices/new" element={<NewInvoice />} />
        <Route path="invoices/edit/:id" element={<EditInvoice />} />
      </Route>
    </Routes>
  )
}

export default App


