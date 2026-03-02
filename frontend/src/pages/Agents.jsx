import { useState, useEffect } from 'react'
import { agentApi } from '../services/api'

function Agents() {
  const [agents, setAgents] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showForm, setShowForm] = useState(false)
  const [editingAgent, setEditingAgent] = useState(null)
  const [formData, setFormData] = useState({ name: '', email: '', phoneNumber: '', address: '' })

  const fetchAgents = async () => {
    try {
      setLoading(true)
      const response = await agentApi.getAll()
      setAgents(response.data || [])
    } catch (err) {
      setError('Error al cargar los agentes')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchAgents()
  }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      if (editingAgent) {
        await agentApi.update(editingAgent.id, formData)
      } else {
        await agentApi.create(formData)
      }
      setFormData({ name: '', email: '', phoneNumber: '', address: '' })
      setShowForm(false)
      setEditingAgent(null)
      fetchAgents()
    } catch (err) {
      setError('Error al guardar el agente')
      console.error(err)
    }
  }

  const handleEdit = (agent) => {
    setEditingAgent(agent)
    setFormData({
      name: agent.name || '',
      email: agent.email || '',
      phoneNumber: agent.phoneNumber || '',
      address: agent.address || ''
    })
    setShowForm(true)
  }

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar este agente?')) {
      try {
        await agentApi.delete(id)
        fetchAgents()
      } catch (err) {
        setError('Error al eliminar el agente')
        console.error(err)
      }
    }
  }

  const handleCancel = () => {
    setShowForm(false)
    setEditingAgent(null)
    setFormData({ name: '', email: '', phoneNumber: '', address: '' })
  }

  if (loading) {
    return <div className="loading">Cargando...</div>
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1>Agentes</h1>
        <button className="btn btn-primary" onClick={() => setShowForm(true)}>
          + Nuevo Agente
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      {showForm && (
        <div className="card">
          <h2>{editingAgent ? 'Editar Agente' : 'Nuevo Agente'}</h2>
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
              <label>Email</label>
              <input
                type="email"
                value={formData.email}
                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label>Teléfono</label>
              <input
                type="text"
                value={formData.phoneNumber}
                onChange={(e) => setFormData({ ...formData, phoneNumber: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label>Dirección</label>
              <input
                type="text"
                value={formData.address}
                onChange={(e) => setFormData({ ...formData, address: e.target.value })}
              />
            </div>
            <div className="actions">
              <button type="submit" className="btn btn-primary">
                {editingAgent ? 'Actualizar' : 'Crear'}
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
              <th>Email</th>
              <th>Teléfono</th>
              <th>Dirección</th>
              <th>Balance</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {agents.map((agent) => (
              <tr key={agent.id}>
                <td>{agent.id}</td>
                <td>{agent.name}</td>
                <td>{agent.email || '-'}</td>
                <td>{agent.phoneNumber || '-'}</td>
                <td>{agent.address || '-'}</td>
                <td>${(agent.balance || 0).toFixed(2)}</td>
                <td className="actions">
                  <button className="btn btn-primary" onClick={() => handleEdit(agent)}>
                    Editar
                  </button>
                  <button className="btn btn-danger" onClick={() => handleDelete(agent.id)}>
                    Eliminar
                  </button>
                </td>
              </tr>
            ))}
            {agents.length === 0 && (
              <tr>
                <td colSpan="7" style={{ textAlign: 'center' }}>
                  No hay agentes registrados
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default Agents






