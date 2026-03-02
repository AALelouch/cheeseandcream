import { useState, useEffect } from 'react'
import { productApi } from '../services/api'

function Products() {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showForm, setShowForm] = useState(false)
  const [editingProduct, setEditingProduct] = useState(null)
  const [formData, setFormData] = useState({ name: '', price: '', cost: '', quantity: '' })

  const fetchProducts = async () => {
    try {
      setLoading(true)
      const response = await productApi.getAll()
      setProducts(response.data || [])
    } catch (err) {
      setError('Error al cargar los productos')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchProducts()
  }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const data = {
        name: formData.name,
        price: parseFloat(formData.price),
        cost: parseFloat(formData.cost),
        quantity: parseFloat(formData.quantity)
      }
      if (editingProduct) {
        await productApi.update(editingProduct.id, data)
      } else {
        await productApi.create(data)
      }
      setFormData({ name: '', price: '', cost: '', quantity: '' })
      setShowForm(false)
      setEditingProduct(null)
      fetchProducts()
    } catch (err) {
      setError('Error al guardar el producto')
      console.error(err)
    }
  }

  const handleEdit = (product) => {
    setEditingProduct(product)
    setFormData({
      name: product.name,
      price: product.price.toString(),
      cost: product.cost?.toString() || '',
      quantity: product.quantity?.toString() || ''
    })
    setShowForm(true)
  }

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar este producto?')) {
      try {
        await productApi.delete(id)
        fetchProducts()
      } catch (err) {
        setError('Error al eliminar el producto')
        console.error(err)
      }
    }
  }

  const handleCancel = () => {
    setShowForm(false)
    setEditingProduct(null)
    setFormData({ name: '', price: '', cost: '', quantity: '' })
  }

  if (loading) {
    return <div className="loading">Cargando...</div>
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1>Productos</h1>
        <button className="btn btn-primary" onClick={() => setShowForm(true)}>
          + Nuevo Producto
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      {showForm && (
        <div className="card">
          <h2>{editingProduct ? 'Editar Producto' : 'Nuevo Producto'}</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Nombre</label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Precio</label>
              <input
                type="number"
                step="0.01"
                value={formData.price}
                onChange={(e) => setFormData({ ...formData, price: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Costo</label>
              <input
                type="number"
                step="0.01"
                value={formData.cost}
                onChange={(e) => setFormData({ ...formData, cost: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label>Cantidad</label>
              <input
                type="number"
                step="0.01"
                value={formData.quantity}
                onChange={(e) => setFormData({ ...formData, quantity: e.target.value })}
                required
              />
            </div>
            <div className="actions">
              <button type="submit" className="btn btn-primary">
                {editingProduct ? 'Actualizar' : 'Crear'}
              </button>
              <button type="button" className="btn" onClick={handleCancel}>
                Cancelar
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="card">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Precio</th>
              <th>Costo</th>
              <th>Cantidad</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>${(product.price || 0).toFixed(2)}</td>
                <td>${(product.cost || 0).toFixed(2)}</td>
                <td>{(product.quantity || 0).toFixed(2)}</td>
                <td className="actions">
                  <button className="btn btn-primary" onClick={() => handleEdit(product)}>
                    Editar
                  </button>
                  <button className="btn btn-danger" onClick={() => handleDelete(product.id)}>
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
            {products.length === 0 && (
              <tr>
                <td colSpan="6" style={{ textAlign: 'center' }}>
                  No hay productos registrados
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default Products







