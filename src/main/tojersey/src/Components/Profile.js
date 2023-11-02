import React from 'react'
import "./styles/Profile.css"
import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';


const Profile = ({profilo}) => {

  console.log(profilo);
  const token = useSelector(state => state.tjStore.token);

  return (
    token ?
    <div className="profile">
        <h1>Profilo</h1>
        <p><strong>Nome Completo: </strong>{profilo.nome+" "+profilo.cognome}</p>
        <p><strong>CF: </strong>{profilo.codiceFiscale}</p>
        <p><strong>Indirizzo: </strong>{profilo.indirizzo+", "+profilo.citta+", "+profilo.provincia+", "+profilo.nazione}</p>
        <p><strong>Email: </strong>{profilo.mail}</p>
        <p><strong>Partita Iva: </strong>{profilo.partitaIva}</p>
        <p><strong>Telefono: </strong>{profilo.telefono}</p>
    </div>
  : <Navigate to="/login" replace /> )
}

export default Profile;
