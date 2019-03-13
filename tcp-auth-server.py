#!/usr/bin/env python
import socketserver
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


def main():
    host = '127.0.0.1'
    port = 5001

    server = ThreadedTCPServer((host, port), ThreadedTCPRequestHandler)

    while True:
        server.handle_request()

    server.close()


class ThreadedTCPRequestHandler(socketserver.BaseRequestHandler):
    def handle(self):
        data = self.request.recv(1024).decode('utf-8')
        response = "Invalid username or password"

        try:
            user = json.loads(data)
            if user in users:
                response = "OK"
        except Exception as e:
            response = str(e)

        self.request.sendall(response.encode('utf-8'))
        self.request.close()


class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    daemon_threads = True


if __name__ == '__main__':
    main()
