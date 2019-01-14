.. figure:: Simba-NS.png
   :align:   center
   
******************
SIMBA CarDemo Android Docs currenctly a placeholder please come back later :)
******************
 
Installation
==============


* Hit the "Clone or Download" button in upper right corner of the github page
* Hit the "Download ZIP" button.
* "Extract the file" with your extractor of choice.
* Open Android Studio and click "Open an existing Android Studio Project."
* Navigate to where the extracted project is and select the project directory of the project (the one that contains the "app" folder) and hit "OK."
* After a brief install, Android Studio should open the project.
* Dependencies


   * This demo app uses the following:
   * Retrofit and gson for GETs and POSTs
   * Web3j, Kethereum, and bitcoinj for the HDwallets


 .. note:: This is a placeholder note incase it is needed
==============

`Here <https://www.youtube.com/watch?v=1BatYaRD60c&list=PLgfX2jfDfJNMEqF_xjZBYmavONXeRK_q5>`_ is a playlist on the SIMBA Chain Youtube channel to get you up to speed on using the dashboard.

.. _contract:
Smart Contract
************

Here is the smart contract I used for Android

.. code-block:: python

   contract Application {
    function Application() public {}

    function carSale (
        string soldTo,
        string amount,
        string _bundleHash
    )
    public {}

    function registerCar (
        string VIN,
        string Make,
        string Model,
        string _bundleHash
    )
    public {}

    function accidentReport (
        string report_name,
        string _bundleHash
    )
    public {}
    }


.. _dashboard:
Creating an app on the SIMBA Dashboard
***************
Before Starting make sure you have an account on the Simba Dashboard and an Ethereum wallet with Ether in it on the Rinkeby testnet

* Create The Smart Contract
* Create The Application
* Configure The Application(Ethereum Blockchain, Rinkeby Network,IPFS Filesystem, Permission disabled)
* Generate API Key(This is not the API name)
.. figure:: APIKey.png
   :align:   center
Converting the Cardemo example to your app
***************
Steps to convert Android cardemo to your own
