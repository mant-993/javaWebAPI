import { createSlice ,createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";


const BASE_URL = "http://localhost:8080/JerseyDemo/webapi"


export const fetchProducts = createAsyncThunk(
    'tjSlice/fetchProducts',
    async(_, thunkAPI) => {
        console.log("fetchProducts");
        const {tjStore} = thunkAPI.getState();
        const response = await axios.get(`${BASE_URL}/prodotti`,
        {headers: { 
            "Content-Type": "application/json",
            "Authorization": `Bearer ${tjStore.token}` 
        }})
        return response.data;
    }
)


export const fetchProductById = createAsyncThunk(
    'tjSlice/fetchProductById',
    async(id, thunkAPI) => {
        const {tjStore} = thunkAPI.getState();
        const response = await axios.get(`${BASE_URL}/prodotti/${id}`,
        {headers: { 
            "Content-Type": "application/json",
            "Authorization": `Bearer ${tjStore.token}` 
        }})
        return response.data;
    }
)



export const fetchProfile = createAsyncThunk(
    'tjSlice/fetchProfile',
    async(id, thunkAPI) => {
        const {tjStore} = thunkAPI.getState();
        const response = await axios.get(`${BASE_URL}/utenti/profilo`,
        {headers: { 
            "Authorization": `Bearer ${tjStore.token}` 
        }})
        return response.data;
    }
)


export const tjSlice = createSlice({
    name : "tjSlice",
    initialState : {
        profile : {},
        token : "",
        products : [],
        product : {},
        loading : false,
        loaded : false,
        error : {}

    },
    reducers : {
        setToken : (state, action) => {
            state.token = action.payload          
        }
    },
    extraReducers : (builder) => {
        builder
            .addCase(fetchProducts.pending, (state, action) => {
                state.loading = true;
                state.loaded = false;
            })
            .addCase(fetchProducts.fulfilled, (state, action) => {
                state.products = [action.payload];
                state.error = undefined;
                state.loading = false;
                state.loaded = true;
            })
            .addCase(fetchProducts.rejected, (state, action) => {
                state.products = [];
                state.error = action.payload;
                state.loading = false;
                state.loaded = true;
            })

            ///////////////////

            .addCase(fetchProductById.pending, (state, action) => {
                state.loading = true;
                state.loaded = false;
            })
            .addCase(fetchProductById.fulfilled, (state, action) => {
                state.product = action.payload;
                state.error = undefined;
                state.loading = false;
                state.loaded = true;
            })
            .addCase(fetchProductById.rejected, (state, action) => {
                state.product = {};
                state.error = action.payload;
                state.loading = false;
                state.loaded = true;
            })

            ///////////////////

            .addCase(fetchProfile.pending, (state, action) => {
                state.loading = true;
                state.loaded = false;
            })
            .addCase(fetchProfile.fulfilled, (state, action) => {
                state.profile = action.payload;
                state.error = undefined;
                state.loading = false;
                state.loaded = true;
            })
            .addCase(fetchProfile.rejected, (state, action) => {
                state.profile = {};
                state.error = action.payload;
                state.loading = false;
                state.loaded = true;
            })
    }

    
})

//export const {fetchAllUsers} = userAlbumPhotoSlice.actions;
export const {setToken} = tjSlice.actions;

export default tjSlice.reducer;