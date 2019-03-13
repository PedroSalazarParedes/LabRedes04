import socket
import json


def main():
    host = '54.212.20.111'
    port = 5001

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as my_socket:
        my_socket.connect((host, port))

        message = {
            'username': 'usuario1',
            'password': 'usuario1'
        }

        my_socket.send(json.dumps(message).encode('utf-8'))
        data = my_socket.recv(1024).decode('utf-8')

        print("Received from server: " + data)


if __name__ == '__main__':
    main()
