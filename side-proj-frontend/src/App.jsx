import { useState } from 'react'
import KamcoHistory from './components/KamcoHistory'
import KamcoCltr from './components/KamcoCltr'
import './App.css'

function App() {
  const [activeTab, setActiveTab] = useState('history')

  return (
    <div className="app">
      <header>
        <h1>KAMCO 공매 정보</h1>
        <nav>
          <button 
            className={activeTab === 'history' ? 'active' : ''}
            onClick={() => setActiveTab('history')}
          >
            이력조회
          </button>
          <button 
            className={activeTab === 'cltr' ? 'active' : ''}
            onClick={() => setActiveTab('cltr')}
          >
            물건명
          </button>
        </nav>
      </header>
      
      <main>
        {activeTab === 'history' && <KamcoHistory />}
        {activeTab === 'cltr' && <KamcoCltr />}
      </main>
    </div>
  )
}

export default App
