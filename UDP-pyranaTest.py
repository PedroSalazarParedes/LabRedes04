import pyrana
import sys

pyrana.setup()

with open("/Users/pedrosalazar/Desktop", "rb") as src:
    dmx = pyrana.Demuxer(src)

