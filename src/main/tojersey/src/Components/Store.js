import { configureStore } from '@reduxjs/toolkit';
import tjSlice from '../Slices/ToJerseySlice';

const Store = configureStore({
    reducer: {
      tjStore : tjSlice
    }
})

export default Store;

