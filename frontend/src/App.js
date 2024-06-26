import './App.css';
import {Component} from "react";
import {RouterComponent} from "./components/RouterComponent";
import {ContextFactoryGenerator} from "./modules/Context/ContextFactory";

class App extends Component {
  render() {
    return (
      <ContextFactoryGenerator baseUrl="http://gatewayserver:8080/bookingshow">
        <RouterComponent/>
      </ContextFactoryGenerator>
    )
  }
}

export default App;
