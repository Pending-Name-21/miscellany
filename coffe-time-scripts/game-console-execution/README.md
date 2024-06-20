# Coffee-Time Script

This guide will help you set up and run the Coffee-Time script. Follow the steps below to ensure everything is configured correctly.

## Steps to Run the Script

1. **Download Required Files**
    - Go to the following link: [Execution Script](https://tree.taiga.io/project/joseluis-teran-coffeetime/us/176)
    - Download the zip file found in the attachment section.

2. **Add the .so File**
    - Extract the zip file and locate the `.so` file.
    - Place the `.so` file into the `console` folder of your project.

3. **Add the .jar File**
    - From the extracted zip, locate the `.jar` file.
    - Place the `.jar` file in the same directory level as the `coffe-time.sh` script.

4. **Update the Script**
    - Open the `coffe-time.sh` script in a text editor.
    - Change the `SOCKET_PATH` variable to `"./"`:
      ```sh
      SOCKET_PATH="./"
      ```

5. **Run the Script**
    - Make the script executable (if not already):
      ```sh
      chmod +x coffe-time.sh
      ```
    - Execute the script:
      ```sh
      ./coffe-time.sh
      ```

6. **Check the Logs**
    - The script outputs logs to the `logs` folder.
    - Check the logs for any messages or errors.

