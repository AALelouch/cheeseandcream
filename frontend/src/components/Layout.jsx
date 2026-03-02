import { Outlet, NavLink } from 'react-router-dom'

function Layout() {
  return (
    <>
      <header className="app-header">
        <h1>🧀 Cheese & Cream</h1>
        <nav className="nav-links">
          <NavLink to="/" className={({ isActive }) => isActive ? 'active' : ''}>
            Dashboard
          </NavLink>
          <NavLink to="/agents" className={({ isActive }) => isActive ? 'active' : ''}>
            Agentes
          </NavLink>
          <NavLink to="/products" className={({ isActive }) => isActive ? 'active' : ''}>
            Productos
          </NavLink>
          <NavLink to="/invoices" className={({ isActive }) => isActive ? 'active' : ''}>
            Facturas
          </NavLink>
        </nav>
      </header>
      <main className="main-content">
        <Outlet />
      </main>
    </>
  )
}

export default Layout

