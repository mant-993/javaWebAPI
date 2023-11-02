import React, {useState} from 'react'
import Grid from '@mui/material/Unstable_Grid2';
import { TextField, Button } from '@mui/material';
import "./styles/Login.css"
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import queryString from 'query-string';
import { setToken } from '../Slices/ToJerseySlice';


const Login = () => {

  const [loginForm, setLoginForm] = useState(
    {
      username : "",
      userPass : "",
    }
  )

  const nav = useNavigate();
  const dispatch = useDispatch();
  

  const handleChange = (e) => {
    const { value, name} = e.target;    
    setLoginForm(prevState => {
        return{                            
            ...prevState,                                        
            [name] : value 
        }
    })
  }

  const checkDisabled = () => {
    return( !loginForm.username || !loginForm.userPass)
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    const {username, userPass} = loginForm;
    axios.post("http://localhost:8080/JerseyDemo/webapi/utenti/login", 
    queryString.stringify({
      username: username,
      userPass: userPass
    }), 
    {headers: { 
      "Content-Type": "application/x-www-form-urlencoded"
    }})
      .then(response => {
          //console.log(response.data);
          dispatch(setToken(response.data));
          nav("/");
      })
      .catch(function (error) {
          //console.log(error)
          alert("Login fallito, riporovare")
      })
  }



  return (
    <div className='login'>
      <Grid container rowSpacing={5} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
        <Grid sm={12} md={12} lg={12}>
          <h1>Login</h1>
        </Grid>
        <Grid sm={12} md={6} lg={6}>
          <TextField required onChange={handleChange} name="username" id="username" label="Username" value={loginForm.username}  />
        </Grid>
        <Grid sm={12} md={6} lg={6}>
          <TextField required onChange={handleChange} name="userPass" id="userPass" label="Password" value={loginForm.userPass}  />
        </Grid>
        <Grid sm={12} md={12} lg={12}>
          <Button disabled={checkDisabled()} onClick={handleSubmit} variant="outlined">Log In</Button>
        </Grid>
      </Grid>

    </div>
  )
}

export default Login;
