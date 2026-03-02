import { useState, useEffect } from 'react'
import { agentApi, productApi, invoiceApi, dashboardApi } from '../services/api'

function Dashboard() {
  const [stats, setStats] = useState({
    agents: 0,
    products: 0,
    invoices: 0,
    totalDebt: 0,
    totalRevenue: 0,
    totalProfit: 0
  })
  const [loading, setLoading] = useState(true)
  const [useFilter, setUseFilter] = useState(false)
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')

  const fetchStats = async (filterByDate = false) => {
    try {
      setLoading(true)

      const [agentsRes, productsRes, invoicesRes] = await Promise.all([
        agentApi.getAll(),
        productApi.getAll(),
        invoiceApi.getAll()
      ])

      let dashboardData
      if (filterByDate && startDate && endDate) {
        const startTime = new Date(startDate).toISOString()
        const endTime = new Date(endDate + 'T23:59:59').toISOString()
        dashboardData = await dashboardApi.getByDateRange(startTime, endTime)
      } else {
        dashboardData = await dashboardApi.getAll()
      }

      setStats({
        agents: agentsRes.data?.length || 0,
        products: productsRes.data?.length || 0,
        invoices: invoicesRes.data?.length || 0,
        totalDebt: dashboardData.data?.totalDebt || 0,
        totalRevenue: dashboardData.data?.totalRevenue || 0,
        totalProfit: dashboardData.data?.totalProfit || 0
      })
    } catch (error) {
      console.error('Error fetching stats:', error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchStats()
  }, [])

  const handleFilter = () => {
    if (startDate && endDate) {
      setUseFilter(true)
      fetchStats(true)
    }
  }

  const handleClearFilter = () => {
    setUseFilter(false)
    setStartDate('')
    setEndDate('')
    fetchStats(false)
  }

  if (loading) {
    return <div className="loading">Cargando...</div>
  }

  return (
    <div>
      <h1>Dashboard</h1>
      <p style={{ color: '#888', marginBottom: '2rem' }}>
        Bienvenido al sistema de facturación de Cheese & Cream
      </p>

      {/* Filtro por fechas */}
      <div className="card" style={{ marginBottom: '2rem' }}>
        <h2>Filtrar por Fechas</h2>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'flex-end', flexWrap: 'wrap' }}>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <label>Fecha Inicio</label>
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
            />
          </div>
          <div className="form-group" style={{ marginBottom: 0 }}>
            <label>Fecha Fin</label>
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
            />
          </div>
          <button className="btn btn-primary" onClick={handleFilter}>
            Filtrar
          </button>
          {useFilter && (
            <button className="btn" onClick={handleClearFilter}>
              Limpiar Filtro
            </button>
          )}
        </div>
        {useFilter && (
          <p style={{ color: '#f5d742', marginTop: '1rem', fontSize: '0.9rem' }}>
            📅 Mostrando datos del {startDate} al {endDate}
          </p>
        )}
      </div>

      <div className="dashboard-cards">
        <div className="card stat-card">
          <div className="stat-value">{stats.agents}</div>
          <div className="stat-label">Agentes</div>
        </div>

        <div className="card stat-card">
          <div className="stat-value">{stats.products}</div>
          <div className="stat-label">Productos</div>
        </div>

        <div className="card stat-card">
          <div className="stat-value">{stats.invoices}</div>
          <div className="stat-label">Facturas</div>
        </div>

        <div className="card stat-card">
          <div className="stat-value" style={{ color: '#dc3545' }}>
            ${stats.totalDebt.toFixed(2)}
          </div>
          <div className="stat-label">Deuda Total</div>
        </div>

        <div className="card stat-card">
          <div className="stat-value" style={{ color: '#17a2b8' }}>
            ${stats.totalRevenue.toFixed(2)}
          </div>
          <div className="stat-label">Ingresos Totales</div>
        </div>

        <div className="card stat-card">
          <div className="stat-value" style={{ color: '#28a745' }}>
            ${stats.totalProfit.toFixed(2)}
          </div>
          <div className="stat-label">Ganancia Total</div>
        </div>
      </div>
    </div>
  )
}

export default Dashboard
