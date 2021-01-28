import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'fontsource-roboto';

ReactDOM.render(
  <React.StrictMode>
    <App
      path={window.location.pathname}
    />
  </React.StrictMode>,
  document.getElementById('root')
);