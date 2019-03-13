#!/usr/bin/env python
import socketserver
from threading import Lock
import json


users = [
    {
        'username': 'usuario1',
        'password': 'usuario1'
    },
    {
        'username': 'usuario2',
        'password': 'usuario2'
    },
    {
        'username': 'usuario3',
        'password': 'usuario3'
    }
]

MAX_NUM_ACTIVE_CONNECTIONS = 150
num_active_connections = 0
connection_lock = Lock()


def main():
    host = '127.0.0.1'
    port = 5001

    server = ThreadedTCPServer((host, port), ThreadedTCPRequestHandler)

    while True:
        server.handle_request()


class ThreadedTCPRequestHandler(socketserver.BaseRequestHandler):
    def handle(self):
        global num_active_connections
        connection_lock.acquire()
        if MAX_NUM_ACTIVE_CONNECTIONS <= num_active_connections:
            connection_lock.release()
            self.request.close

        else:
            num_active_connections = num_active_connections + 1
            connection_lock.release()
            data = self.request.recv(1024).decode('utf-8')
            response = "Invalid username or password"

            try:
                user = json.loads(data)
                if user in users:
                    response = "OK"
            except Exception as e:
                response = str(e)

            self.request.sendall(response.encode('utf-8'))

            connection_lock.acquire()
            num_active_connections = num_active_connections - 1
            connection_lock.release()

            self.request.close()


class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    daemon_threads = True


if __name__ == '__main__':
    main()
