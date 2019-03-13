import socket


def main():
    host = '127.0.0.1'
    port = 5001

    my_socket = socket.socket()
    my_socket.bind((host, port))

    my_socket.listen(1)

    try:
        conn, addr = my_socket.accept()
        print("Connection from: " + str(addr))

        while True:
            data = conn.recv(1024).decode()
            if not data:
                break
            print("from connected user: " + str(data))

            data = str(data).upper()
            print("Received from User: " + str(data))

            data = input(" ? ")
            conn.send(data.encode())

        conn.close()

    finally:
        conn.close()


if __name__ == '__main__':
    main()
