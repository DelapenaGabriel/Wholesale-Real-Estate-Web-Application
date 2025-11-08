import axios from 'axios';

export default{

    getProperties(){
        return axios.get('/api/property')
    },

    getProperty(id){
        return axios.get(`/api/property/${id}`)
    },

    createProperty(property){
        return axios.post('/api/property', property)
    },

    deleteProperty(id){
        return axios.delete(`/api/property/${id}`)
    },

    updateProperty(id, property){
        return axios.put(`/api/property/${id}`, property)
    }


}