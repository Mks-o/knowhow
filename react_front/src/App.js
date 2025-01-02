import './App.css';
import React, { useState } from 'react';
import { base_url, fetch_something, get_request_srcs_url, get_request_url, get_top_ten_requests_url, resource_info } from './utils/constants';
import * as styles from './utils/bootstrapclasses';

const App = () => {
  const [request_body, setRequest] = useState("");
  const [response, setResponce] = useState();
  const [image_index, setImageIndex] = useState(0);
  const [lastRequests, setLastRequests] = useState(null);
  const [images_srcs, setImagesSrcs] = useState([]);

  const searchFunc = async (request_body) => {
    let body_value = {
      id: null,
      request: request_body
    }
    await fetch_something(get_request_url, body_value, "POST")
      .then(data => {
        data.forEach(element => {
          let x = JSON.parse(element.imagessrc);
          element.imagessrc = x;
        });
        
        setResponce(data)
      })
      .catch(error => console.error(error));
  }
  const rate_answer = async (id) => {
    if (!id) return console.log("id null");

    const url = base_url + "rate_answer/" + id;
    await fetch_something(url, "", "POST");
    await searchFunc(request_body);
  }
  const changeImageSrc = (array) => {
    let current_array_length = array.length - 1;
    // console.log(image_index);

    setImageIndex(current_array_length > image_index ? 1 + image_index : 0);
  }
  const getLastRequests = async () => {
    await fetch_something(get_top_ten_requests_url, null, "GET").then(data => {
      setLastRequests(data);
    }).catch(error => console.error(error));
  }
  const getrequest_srcs = async (id, i) => {
    await fetch_something(get_request_srcs_url + id, "", "GET").then(data => {
      const js = JSON.parse(data[0]);
      // console.log(data, js);
      setImagesSrcs(js)
    })
      .catch(res => console.error(res))
  }

  const load_last_requests = () => {
    return lastRequests && lastRequests?.map((value, index) => {
      return <button className={styles.last_requests} key={index}
        onClick={() => getrequest_srcs(value.request_id, index)}>{value.request}</button>
    })
  }
  const last_requests_images = () => {
    return images_srcs &&
      <img className={styles.card_last_requests}
        key={image_index}
        alt={images_srcs[0]}
        src={images_srcs[image_index]}
        onClick={() => changeImageSrc(images_srcs)}></img>
  }
  const search_components = () => {
    return <div className='input-group'>
      <input type='text' defaultValue={request_body||""}
      placeholder='Enter your request' onChange={(e) => setRequest(e.currentTarget.value)}/>
      <button className='btn btn-warning border' onClick={() => getLastRequests()}>GetLastResults</button>
      <button className='btn btn-success border' onClick={() => searchFunc(request_body)}>Search</button>
    </div>
  }
  const checkRequest=(data)=>{
    let words = data.split(' ');
    return words.map((value,index)=>{
      if(value==='—')return"No unswer";
      return <label 
      className='p-1 me-1 bg-warning border rounded border-dark text-light'
      key={index}
      onClick={()=>{
        let req = value.replaceAll(/[^a-zA-Z]/g,"");
        setResponce("");
        setRequest(req);
        searchFunc(req);
      }}>
        {value}
        </label>
    })
  }
  const main_card_component = () => {
    return <div className={styles.card_main}>
      {response && response.sort((x, y) => y.raiting - x.raiting).map((answer_data, answer_index) => {
        return <div key={answer_index} className={styles.card_header}>
          id{answer_data.answer_id} <p className={styles.card_date}>{answer_data.cretedDate}</p>
          <a className={styles.card_link} rel='noreferrer' target='_blank' href={answer_data.answer_url}>{answer_data.answer_url}</a>
          <p>{checkRequest(answer_data.answer)}</p>
          <p className={styles.d_block}>Raiting: {answer_data.raiting}
            <button className={styles.rate}
              onClick={() => { rate_answer(answer_data.answer_id); }}> Rate answer !</button></p>
        </div>
      })}
      {!response && lastRequests && <div className='spinner-border'></div>}
      {response && <div className={styles.card_body}>
        <a target='_blank' rel='noreferrer' href={response[0]?.imagessrc[image_index]}>{"https://unsplash.com/s/photos/"+ request_body}</a>
        <img
          className={styles.card_image}
          key={response ? [0] : 0}
          alt={response ? [0] : "No content"}
          src={response[0]?.imagessrc[image_index]}
          onClick={() => changeImageSrc(response[0].imagessrc)}></img>
      </div>}
    </div>
  }
  return <div className={styles.app}>Last 10 requests:
    {load_last_requests()}
    {last_requests_images()}
    {resource_info}
    {search_components()}
    {main_card_component()}
  </div>
}
export default App;
