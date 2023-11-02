import React from 'react'
import { Card, CardContent, Typography, CardMedia} from '@mui/material';
import './styles/Products.css'
import { useSelector } from 'react-redux';
import { Navigate } from "react-router-dom";


const ProductDetail = ({prodotto}) => {

  const token = useSelector(state => state.tjStore.token)

  return (
    token ?
    <div className='productDetail'>
      <Card className="card" key={prodotto.idProdotto}>
        <CardMedia
          component="img"
          alt="green iguana"
          height="250"
          image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSoRpVwrFFOg0anfJDWWIfqumkt-J63Nstutg&usqp=CAU"
        />
        <CardContent>
          <Typography sx={{ fontWeight: 'bold' }} color="text.secondary">
            {prodotto.titolo}
          </Typography>
          <Typography sx={{ m:2, typography: 'subtitle2' }} color="text.secondary">
            {prodotto.sottotitolo}
          </Typography>
          <Typography sx={{ m:3,  fontWeight: 'light'  }} color="text.secondary">
            {prodotto.descrizione}
          </Typography>
          <Typography sx={{ m: 2, fontFamily: 'Monospace' }} color="text.secondary">
            Aliquota: {prodotto.aliquota}
          </Typography>
          <Typography sx={{ m: 2, fontFamily: 'Monospace' }} color="text.secondary">
            Prezzo: {prodotto.prezzo} €
          </Typography>
        </CardContent>
      </Card>
    </div>
    : <Navigate to="/login" replace /> )
}

export default ProductDetail;
