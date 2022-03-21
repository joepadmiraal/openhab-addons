# Eufy Binding

Eufy by Anker is a set of security products with amongst others, camera's, doorbells and homestations. 
In contrast with other providers of these kinds of products, Eufy products can be used without a subscription service.
It can save images and videos on HomeBase devices which can be kept on premise instead of in the cloud.
There is a cloud connection for live views and push messages in the Eufy Security app, e.g. when the doorbell rings or an alarm triggers when you are on the road.
There is an open source project connecting to just these cloud services, to view push messages and set basic settings like turn on motion detection, set guard mode etc.

For this binding to work you will need to run a docker image as described here:
``https://hub.docker.com/r/bropat/eufy-security-ws``
In short, you start the docker image with just two parameters: the username and password of your Eufy account. 
The docker container is a wrapper around the Eufy client ``https://www.npmjs.com/package/eufy-security-client``

Do not enable 2 factor authentication on this account.
It is supported by the container but not implemented in this biding.

## Supported Things

The binding connects to a docker container using a Bridge Thing. 
The device types can be divided in two categories: Stations and Cameras.

A Station is a device that can connect to a network (wired or wifi) on one side and provides a secure wireless network for the other type of devices, Cameras. 
A Station can save video and audio, and determines the running mode of your installation (e.g. Home / Away). 
It may also have a speaker for alarms and doorbell chimes.

A Camera is a device that can record video, has sensors and output channels. 
Depending on type and model, it can contain movement sensors, auto night vision, anti-theft mode, and support two way communication with a microphone and a speaker.

Currently the following devices are tested and confirmed: EufyCam Pro 2, Eufy Video Doorbell, Eufy Doorbell Chime, Eufy HomeBase.
The interface is fairly generic, so there is a good chance most other recent Eufy models will work too, although some channels might not function (e.g. if nightvision is not supprted by the model). 

## Discovery

The binding connects to a docker container using a Bridge Thing. 
When connected, the binding will discover all devices in the account of that container and add them to the OpenHAB inbox.

## Binding Configuration

_If your binding requires or supports general configuration settings, please create a folder ```cfg``` and place the configuration file ```<bindingId>.cfg``` inside it. In this section, you should link to this file and provide some information about the options. The file could e.g. look like:_



_Note that it is planned to generate some part of this based on the information that is available within ```src/main/resources/OH-INF/binding``` of your binding._

_If your binding does not offer any generic configurations, you can remove this section completely._

## Thing Configuration

_Describe what is needed to manually configure a thing, either through the UI or via a thing-file. This should be mainly about its mandatory and optional configuration parameters. A short example entry for a thing file can help!_

_Note that it is planned to generate some part of this based on the XML files within ```src/main/resources/OH-INF/thing``` of your binding._

## Channels

_Here you should provide information about available channel types, what their meaning is and how they can be used._

_Note that it is planned to generate some part of this based on the XML files within ```src/main/resources/OH-INF/thing``` of your binding._

| channel  | type   | description                  |
|----------|--------|------------------------------|
| control  | Switch | This is the control channel  |

## Full Example

_Provide a full usage example based on textual configuration files (*.things, *.items, *.sitemap)._

## Any custom content here!

Do not enable 2 factor authentication on this account.
It is supported by the container but not implemented in this biding.

_Feel free to add additional sections for whatever you think should also be mentioned about your binding!_

