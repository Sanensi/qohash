import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'fontsource-roboto';

function Root() {
  const [path, setPath] = useState(window.location.pathname);

  const changeFolder = (p: string) => {
    window.history.pushState("", "", `${window.origin}/${p}`);
    setPath(window.location.pathname);
  }

  useEffect(() => {
    const popState = () => setPath(window.location.pathname);
    window.addEventListener("popstate", popState);
    return () => window.removeEventListener("popstate", popState);
  },
    []
  );

  return (<React.StrictMode>
    <App
      path={path}
      onFolderChange={changeFolder}
    />
  </React.StrictMode>);
}

ReactDOM.render(<Root />, document.getElementById('root'));