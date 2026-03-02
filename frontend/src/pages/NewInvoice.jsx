import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { agentApi, productApi, invoiceApi } from '../services/api'

function NewInvoice() {
  const navigate = useNavigate()
  const [agents, setAgents] = useState([])
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [submitting, setSubmitting] = useState(false)

  const [selectedAgent, setSelectedAgent] = useState('')
  const [invoiceProducts, setInvoiceProducts] = useState([])
  const [invoiceType, setInvoiceType] = useState('SALE')
  const [hasBalance, setHasBalance] = useState(false)
  const [balanceAmount, setBalanceAmount] = useState('')

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [agentsRes, productsRes] = await Promise.all([
          agentApi.getAll(),
          productApi.getAll()
        ])
        setAgents(agentsRes.data || [])
        setProducts(productsRes.data || [])
      } catch (err) {
        setError('Error al cargar datos')
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchData()
  }, [])

  const handleAddProduct = () => {
    setInvoiceProducts([...invoiceProducts, { productId: '', quantity: 1 }])
  }

  const handleRemoveProduct = (index) => {
    setInvoiceProducts(invoiceProducts.filter((_, i) => i !== index))
  }

  const handleProductChange = (index, field, value) => {
    const updated = [...invoiceProducts]
    updated[index][field] = field === 'quantity' ? parseInt(value) || 0 : value
    setInvoiceProducts(updated)
  }

  const calculateTotal = () => {
    return invoiceProducts.reduce((total, item) => {
      const product = products.find(p => p.id.toString() === item.productId.toString())
      if (product && item.quantity) {
        return total + (product.price * item.quantity)
      }
      return total
    }, 0)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!selectedAgent) {
      setError('Selecciona un agente')
      return
    }

    if (invoiceProducts.length === 0) {
      setError('Agrega al menos un producto')
      return
    }

    const invalidProducts = invoiceProducts.filter(p => !p.productId || p.quantity <= 0)
    if (invalidProducts.length > 0) {
      setError('Todos los productos deben tener un producto seleccionado y cantidad mayor a 0')
      return
    }

    setSubmitting(true)
    setError(null)

    try {
      const invoiceData = {
        invoiceType: invoiceType,
        agentId: parseInt(selectedAgent),
        products: invoiceProducts.map(item => ({
          productId: parseInt(item.productId),
          quantity: item.quantity
        })),
        hasBalance: hasBalance,
        balanceAmount: hasBalance ? parseFloat(balanceAmount) : null
      }

      await invoiceApi.create(invoiceData)
      navigate('/invoices')
    } catch (err) {
      setError('Error al crear la factura: ' + (err.response?.data?.message || err.message))
      console.error(err)
    } finally {
      setSubmitting(false)
    }
  }

  if (loading) {
    return <div className="loading">Cargando...</div>
  }

  return (
    <div>
      <h1>Nueva Factura</h1>

      {error && <div className="error">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="card">
          <h2>Información General</h2>
          <div className="form-group">
            <label>Tipo de Factura</label>
            <select
              value={invoiceType}
              onChange={(e) => setInvoiceType(e.target.value)}
              required
            >
              <option value="SALE">Venta</option>
              <option value="PURCHASE">Compra</option>
            </select>
          </div>
          <div className="form-group">
            <label>Agente</label>
            <select
              value={selectedAgent}
              onChange={(e) => setSelectedAgent(e.target.value)}
              required
            >
              <option value="">Seleccionar agente...</option>
              {agents.map((agent) => (
                <option key={agent.id} value={agent.id}>
                  {agent.name}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <input
                type="checkbox"
                checked={hasBalance}
                onChange={(e) => {
                  setHasBalance(e.target.checked)
                  if (!e.target.checked) setBalanceAmount('')
                }}
                style={{ width: 'auto' }}
              />
              ¿Tiene saldo pendiente?
            </label>
          </div>
          {hasBalance && (
            <div className="form-group">
              <label>Monto del saldo pendiente</label>
              <input
                type="number"
                step="0.01"
                min="0"
                value={balanceAmount}
                onChange={(e) => setBalanceAmount(e.target.value)}
                placeholder="Ingrese el monto a deber"
                required={hasBalance}
              />
            </div>
          )}
        </div>

        <div className="card">
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <h2>Productos</h2>
            <button type="button" className="btn btn-primary" onClick={handleAddProduct}>
              + Agregar Producto
            </button>
          </div>

          {invoiceProducts.length === 0 ? (
            <p style={{ color: '#888', marginTop: '1rem' }}>
              No hay productos agregados. Haz clic en "Agregar Producto" para comenzar.
            </p>
          ) : (
            <table className="table">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th>Precio</th>
                  <th>Cantidad</th>
                  <th>Subtotal</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {invoiceProducts.map((item, index) => {
                  const selectedProduct = products.find(p => p.id.toString() === item.productId.toString())
                  const subtotal = selectedProduct ? selectedProduct.price * item.quantity : 0

                  return (
                    <tr key={index}>
                      <td>
                        <select
                          value={item.productId}
                          onChange={(e) => handleProductChange(index, 'productId', e.target.value)}
                          required
                          style={{ width: '100%' }}
                        >
                          <option value="">Seleccionar...</option>
                          {products.map((product) => (
                            <option key={product.id} value={product.id}>
                              {product.name} (Disp: {product.quantity})
                            </option>
                          ))}
                        </select>
                      </td>
                      <td>${selectedProduct ? selectedProduct.price.toFixed(2) : '0.00'}</td>
                      <td>
                        <input
                          type="number"
                          min="1"
                          value={item.quantity}
                          onChange={(e) => handleProductChange(index, 'quantity', e.target.value)}
                          required
                          style={{ width: '80px' }}
                        />
                      </td>
                      <td>${subtotal.toFixed(2)}</td>
                      <td>
                        <button
                          type="button"
                          className="btn btn-danger"
                          onClick={() => handleRemoveProduct(index)}
                        >
                          ✕
                        </button>
                      </td>
                    </tr>
                  )
                })}
              </tbody>
              <tfoot>
                <tr>
                  <td colSpan="3" style={{ textAlign: 'right', fontWeight: 'bold' }}>
                    Total:
                  </td>
                  <td style={{ fontWeight: 'bold', color: '#f5d742' }}>
                    ${calculateTotal().toFixed(2)}
                  </td>
                  <td></td>
                </tr>
              </tfoot>
            </table>
          )}
        </div>

        <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
          <button type="submit" className="btn btn-primary" disabled={submitting}>
            {submitting ? 'Creando...' : 'Crear Factura'}
          </button>
          <button type="button" className="btn" onClick={() => navigate('/invoices')}>
            Cancelar
          </button>
        </div>
      </form>
    </div>
  )
}

export default NewInvoice





