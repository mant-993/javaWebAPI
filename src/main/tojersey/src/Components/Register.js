import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Grid from '@mui/material/Unstable_Grid2';
import { TextField, Button } from '@mui/material';
import "./styles/Register.css"
import axios from 'axios';


const Register = () => {


  const [registrationForm, setRegistrationForm] = useState(
    {
     cognome : "",
     nome : "",
     codiceFiscale : "",
     partitaIva : "",
     mail : "",
     indirizzo : "",
     cap : "",
     citta : "",
     provincia : "",
     nazione : "",
     telefono : "",
     username : "",
     userPass : "",
     privacy : 1
    }
  )

  const nav = useNavigate();

  const handleChange = (e) => {
    const { value, name} = e.target;    
    setRegistrationForm(prevState => {
        return{                            
            ...prevState,                                        
            [name] : value 
        }
    })
  }

  const checkDisabled = () => {
    return( !registrationForm.cognome || !registrationForm.nome || !registrationForm.codiceFiscale || !registrationForm.partitaIva ||
      !registrationForm.mail || !registrationForm.indirizzo || !registrationForm.cap || !registrationForm.citta ||
      !registrationForm.provincia || !registrationForm.nazione || !registrationForm.telefono || !registrationForm.username ||
      !registrationForm.userPass || !registrationForm.privacy )
  }

  const handleSubmit = () => {
    axios.post("http://localhost:8080/JerseyDemo/webapi/utenti", JSON.stringify(registrationForm), 
    {headers: {
      'Content-Type': 'application/json'
    }})
      .then(response => {
          //console.log(response.data)
          alert("Registrazione effettuata con successo")
          nav("/");
      })
      .catch(function (error) {
          //console.log(error)
          alert("Registrazione fallita")
      })
  }



  return (
    <div className='register'>
      <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
        <Grid md={12} lg={12}>
          <h1>Registrazione</h1>
        </Grid>
        <Grid sm={12} md={6} lg={3}>
          <TextField required onChange={handleChange} name="cognome" id="cognome" label="Cognome" value={registrationForm.cognome}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="nome" id="nome" label="Nome" value={registrationForm.nome}   />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="codiceFiscale" id="codiceFiscale" label="Codice Fiscale" value={registrationForm.codiceFiscale}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="partitaIva" id="partitaIva" label="Partita Iva" value={registrationForm.partitaIva}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="mail"id="mail" label="Mail" value={registrationForm.mail}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="indirizzo" id="indirizzo" label="Indirizzo" value={registrationForm.indirizzo}   />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="cap" id="cap" label="Cap" value={registrationForm.cap}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="citta" id="città" label="Città" value={registrationForm.citta}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="provincia" id="provincia" label="Provincia" value={registrationForm.provincia}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="nazione" id="nazione" label="Nazione" value={registrationForm.nazione}  />
        </Grid>
        <Grid sm={12}  md={6} lg={3}>
          <TextField required onChange={handleChange} name="telefono" id="telefono" label="Telefono" value={registrationForm.telefono}  />
        </Grid>
        <Grid sm={12}  md={6} lg={6}>
          <TextField required onChange={handleChange} name="username" id="username" label="Username" value={registrationForm.username}  />
        </Grid>
        <Grid sm={12}  md={6} lg={6}>
          <TextField required onChange={handleChange} name="userPass" id="userPass" label="Password" value={registrationForm.userPass}  />
        </Grid>
        <Grid sm={12}  md={12} lg={12}>
          <Button disabled={checkDisabled()} onClick={handleSubmit} variant="outlined">Register</Button>
        </Grid>
      </Grid>

    </div>
  )
}

export default Register;
