import React from 'react'
import { Card, CardContent, Typography, CardActions, Button} from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2'; // Grid version 2
import { useSelector } from 'react-redux';
import { Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import "./styles/Products.css"





const Products = ({prodotti}) => {

  const token = useSelector(state => state.tjStore.token)
  const nav = useNavigate();

  const navToProduct = (id) => {
    nav(`./${id}`)
  }


  
  return (
    token ?
    <div className='products'>
      <Grid container spacing={1} height={200}>
    {prodotti?.map(function(prodotto) {
      return (
          <Grid xs={12} sm={6} md={6} lg={4} xl={3} key={prodotto.idProdotto}>
            <Card className="card" >
              <CardContent>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                  {prodotto.titolo}
                </Typography>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                  {prodotto.sottotitolo}
                </Typography>
              </CardContent>
              <CardActions style={{justifyContent: 'center'}}>
                <Button size="small" onClick={()=>navToProduct(prodotto.idProdotto)}>Details</Button>
              </CardActions>
            </Card>
          </Grid> 
      )
    })}
    </Grid>
    </div>
    : <Navigate to="/login" replace /> )
}

export default Products;

