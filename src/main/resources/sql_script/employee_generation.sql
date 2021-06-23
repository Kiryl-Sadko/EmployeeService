do
$$
    DECLARE
i integer := 0;
begin
        WHILE
i < 5
            loop
                INSERT INTO public.employee(first_name,
                                            last_name,
                                            department_id,
                                            job_title,
                                            gender,
                                            date_of_birth)
                VALUES (concat('first_name_', i),
                        concat('last_name', i),
                        i,
                        concat('developer_', i),
                        'female',
                        '1995-08-03');
                i
:= i + 1;
END loop;
end
$$;