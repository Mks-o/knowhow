export const base_url = 'http://localhost:8080/';
export const get_request = 'send_request';
export const get_request_url = base_url + get_request;
export const get_top_ten_requests_url = base_url + "get_top_ten_requests";
export const get_request_srcs_url = base_url + "get_request_srcs/"

export const set_headers = (auth) => {
    let headers = {
        "Accept": "*/*",
        "Accept-Encoding": "gzip, deflate, br",
        "Connection": "keep-alive",
        "Content-Type": "application/json",
        "Cache-Control": "no-cache"
    }
    if (auth != null) headers.Authorization = auth
    return headers;
}

export async function fetch_something(url, body_value, method) {
    let headers_values = {
        headers: set_headers(),
        method:  method
    }
    if (body_value) headers_values.body = JSON.stringify(body_value)
    //console.log(headers_values);

    const response = await fetch((url), headers_values );
    if (!response.ok) {
        throw new Error(`HTTP error: ${response.status}`);
    }
    const data = await response.json();
    return data;
}

export const resource_info = <p>All info from <a rel='noreferrer' target='_blank' href='www.google.com'>google.com</a>, all images content from <a href='https://unsplash.com/' rel='noreferrer' target='_blank'>unsplash.com</a> <a href='https://unsplash.com/privacy' rel='noreferrer' target='_blank'>license and privacy</a></p>