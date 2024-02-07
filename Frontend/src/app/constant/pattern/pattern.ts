export const PATTERN = {
    email:/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
    password:  /^[^\s]+$/,
    name:/(.|\s)*\S(.|\s)*/,
    // phone: /^[0-9]+$/,
    phone:/^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/,
    id:/[0-9]{1,3}/,
    age:/[0-9]+/
    // price:/(^[0][1-9]+)|([1-9]\d*)+$/
}; 