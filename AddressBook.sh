#!/bin/bash

ADDRESS_BOOK="address_book.txt"

# Function to display menu
display_menu() {
    echo "Address Book Menu:"
    echo "a) Create Address Book"
    echo "b) View Address Book"
    echo "c) Insert a Record"
    echo "d) Delete a Record"
    echo "e) Modify a Record"
    echo "f) Exit"
    echo -n "Choose an option: "
}

# Function to create address book (clear existing)
create_address_book() {
    > $ADDRESS_BOOK  # Create or clear the address book file
    echo "Address book created."
}

# Function to view address book
view_address_book() {
    if [[ ! -s $ADDRESS_BOOK ]]; then
        echo "Address book is empty."
    else
        echo "Address Book Records:"
        cat $ADDRESS_BOOK | nl  # Display file content with line numbers
    fi
}

# Function to insert a record
insert_record() {
    echo -n "Enter name: "
    read name
    echo -n "Enter phone number: "
    read phone
    echo -n "Enter email: "
    read email

    echo "$name, $phone, $email" >> $ADDRESS_BOOK
    echo "Record inserted."
}

# Function to delete a record
delete_record() {
    if [[ ! -s $ADDRESS_BOOK ]]; then
        echo "Address book is empty."
    else
        view_address_book
        echo -n "Enter the record number to delete: "
        read record_number
        sed -i "${record_number}d" $ADDRESS_BOOK
        echo "Record deleted."
    fi
}

# Function to modify a record
modify_record() {
    if [[ ! -s $ADDRESS_BOOK ]]; then
        echo "Address book is empty."
    else
        view_address_book
        echo -n "Enter the record number to modify: "
        read record_number

        echo -n "Enter new name: "
        read new_name
        echo -n "Enter new phone number: "
        read new_phone
        echo -n "Enter new email: "
        read new_email

        new_record="$new_name, $new_phone, $new_email"
        sed -i "${record_number}s/.*/$new_record/" $ADDRESS_BOOK
        echo "Record modified."
    fi
}

CORRECT_PASSWORD="Tanmay"

# Main loop to interact with the user
echo -n "Enter password:"
read -s  password
if [[ "$password" == "$CORRECT_PASSWORD" ]]; then

  while true; do
    display_menu
    read option
    case $option in
        a) create_address_book ;;
        b) view_address_book ;;
        c) insert_record ;;
        d)delete_record;;
	f) echo"Exiting...";exit 0;;
	*) echo "Invalid option. Please Try again.";;
	
    esac
  done
else
	echo "Incorrect Password. Access denied"
	exit 1

fi

done

