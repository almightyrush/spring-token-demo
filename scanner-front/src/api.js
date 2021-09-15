import axios from 'axios'

export default axios.create({
    baseURL: 'http://localhost:8080/'
    // baseURL: 'http://6559-203-187-238-115.ngrok.io/' 
});
