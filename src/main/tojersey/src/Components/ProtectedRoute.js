import React, {useEffect} from 'react'
import { useSelector, useDispatch } from 'react-redux';
import { Navigate } from "react-router-dom";
import { CircularProgress } from '@mui/material';
import { useParams } from 'react-router-dom';




const ProtectedRoute = ({ requestFunc, childName, children }) => {
  
  const dispatch = useDispatch();
  const {profile, products, product, loading, error} = useSelector(state => state.tjStore);
  const { product_id } = useParams();  

  console.log(requestFunc, children);


  useEffect(()=>{
    switch(childName){
      case "Products":
        dispatch(requestFunc())
        break;
      case "ProductDetail":
        dispatch(requestFunc(product_id))
        break;
      case "Profile":
        dispatch(requestFunc())
        break;
      default:
        console.log("No Children");
    }
  }, [requestFunc, dispatch, product_id, children])

  //console.log(childName, products, product)

  const prodotti = Object.values(products)[0];

  switch(childName){
    case "Products":
      return(
        loading ? <CircularProgress /> : error ? <Navigate to="/login" replace /> : React.cloneElement(children, { prodotti: prodotti})
      )
    case "ProductDetail":
      return(
        loading ? <CircularProgress /> : error ? <Navigate to="/login" replace /> : React.cloneElement(children, { prodotto : product})
      )
    case "Profile":
      return(
        loading ? <CircularProgress /> : error ? <Navigate to="/login" replace /> : React.cloneElement(children, { profilo : profile})
      )
    default:
      console.log("No Children");
  } 
  
}

export default ProtectedRoute;
