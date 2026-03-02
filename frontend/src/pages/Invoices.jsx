import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { invoiceApi } from '../services/api'

function Invoices() {
  const [invoices, setInvoices] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  const fetchInvoices = async () => {
    try {
      setLoading(true)
      const response = await invoiceApi.getAll()
      setInvoices(response.data || [])
    } catch (err) {
      setError('Error al cargar las facturas')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchInvoices()
  }, [])

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar esta factura?')) {
      try {
        await invoiceApi.delete(id)
        fetchInvoices()
      } catch (err) {
        setError('Error al eliminar la factura')
        console.error(err)
      }
    }
  }

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A'
    try {
      const date = new Date(dateString)
      if (isNaN(date.getTime())) return 'N/A'
      return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      })
    } catch {
      return 'N/A'
    }
  }

  const handleEdit = (id) => {
    navigate(`/invoices/edit/${id}`)
  }

  if (loading) {
    return <div className="loading">Cargando...</div>
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1>Facturas</h1>
        <Link to="/invoices/new" className="btn btn-primary">
          + Nueva Factura
        </Link>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="card">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tipo</th>
              <th>Agente</th>
              <th>Fecha Creación</th>
              <th>Última Modificación</th>
              <th>Total</th>
              <th>Saldo Pendiente</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {invoices.map((invoice) => (
              <tr key={invoice.id}>
                <td>{invoice.id}</td>
                <td>{invoice.invoiceType === 'SALE' ? 'Venta' : 'Compra'}</td>
                <td>{invoice.agentName || 'N/A'}</td>
                <td>{formatDate(invoice.creationDate)}</td>
                <td>{invoice.modifiedDate ? formatDate(invoice.modifiedDate) : '-'}</td>
                <td>${(invoice.totalAmount || 0).toFixed(2)}</td>
                <td style={{ color: invoice.balance > 0 ? '#dc3545' : '#28a745' }}>
                  {invoice.balance > 0 ? `$${invoice.balance.toFixed(2)}` : 'Pagado'}
                </td>
                <td className="actions">
                  <button className="btn btn-primary" onClick={() => handleEdit(invoice.id)}>
                    Editar
                  </button>
                  <button className="btn btn-danger" onClick={() => handleDelete(invoice.id)}>
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
            {invoices.length === 0 && (
              <tr>
                <td colSpan="8" style={{ textAlign: 'center' }}>
                  No hay facturas registradas
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default Invoices


