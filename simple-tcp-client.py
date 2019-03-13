import socket

def main():
    host = '127.0.0.1'
    port = 5001

    my_socket = socket.socket()
    my_socket.connect((host, port))

    message = input(" ? ")

    while message != 'q':
        my_socket.send(message.encode())
        data = my_socket.recv(1024).decode()

        print("Received from server: " + data)
        message = input(" ? ")

    my_socket.close()


if __name__ == '__main__':
    main()
