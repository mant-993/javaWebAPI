import './App.css';
import { BrowserRouter, Link, Routes, Route } from 'react-router-dom';
import Home from './Components/Home';
import Login from './Components/Login';
import Register from './Components/Register';
import Products from './Components/Products';
import ProductDetail from './Components/ProductDetail';
import ProtectedRoute from './Components/ProtectedRoute';
import Profile from './Components/Profile';
import { useSelector, useDispatch } from 'react-redux';
import { setToken } from './Slices/ToJerseySlice';
import { fetchProducts } from './Slices/ToJerseySlice';
import { fetchProductById } from './Slices/ToJerseySlice';
import { fetchProfile } from './Slices/ToJerseySlice';


function App() {

  const token = useSelector(state => state.tjStore.token)
  const dispatch = useDispatch();


  const logOut = () => {
    dispatch(setToken(""))
  }

  return (
    <BrowserRouter>
      <div className='app'>
        <section className='nav'>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/register">Register</Link>
              </li>
              <li>
                <Link to="/products">Products</Link>
              </li>
              {token &&
                <li>
                  <div className="dropdown">
                    <button className="dropbtn">Utente</button>
                    <div className="dropdown-content">
                      <Link to="/profile">Profilo</Link>
                      <Link onClick={logOut}>Logout</Link>
                    </div>
                  </div>
                </li>
              }
              
              
            </ul>
        </section>
        <section className='content'>
            <Routes>
              <Route index element={ < Home />} />
              <Route path="/login" element={ < Login />} />
              <Route path="/register" element={ < Register />} />
              <Route path="/profile" element={ <ProtectedRoute requestFunc={fetchProfile} childName="Profile"><Profile/></ProtectedRoute> } />
              <Route path="/products" element={ < ProtectedRoute requestFunc={fetchProducts} childName="Products"><Products/></ProtectedRoute> } />
              <Route path="/products/:product_id" element={ < ProtectedRoute requestFunc={fetchProductById} childName="ProductDetail"><ProductDetail/></ProtectedRoute> } />
            </Routes>
        </section>
        <section className='footer'>
          @Copyright 2023
        </section>
      </div>
    </BrowserRouter>  
  );
}

export default App;


